var Stomp = require('@stomp/stompjs');
var SockJS = require('sockjs-client');
Object.assign(global, { WebSocket: require('websocket').w3cwebsocket });

const apiServer = ""; //Our Spring-Boot application's address

const stompConfig = {
    reconnectDelay: 1000,
    heartbeatIncoming: 0,
    heartbeatOutgoing: 0
}

stompConfig.webSocketFactory = () => {
  return new SockJS(
      apiServer + "/mws"
  );
};

let stompClient = new Stomp.Client(stompConfig);

const helpers = {
    connectStomp: function(props){
      stompClient.onConnect = function(frame) {
        stompClient.subscribe("/suggesting/updates", function(message) {
            //This is called when a message from the WSS topic is received
            var dataFromServer = JSON.parse(message.body);
            var newSuggestions = JSON.parse(dataFromServer.suggestions);
            props.pass.setState({
              suggestions: newSuggestions
            });
        });
      }
      stompClient.onStompError = function(frame) {
        console.error(frame);
      };

      stompClient.onWebSocketError = function(frame) {
        console.error(frame);
      };

      stompClient.onWebSocketClose = function(frame) {
        console.info(frame);
      };

      stompClient.onDisconnect = function(frame) {
        console.info(frame);
      };

      //Small delay to prevent React from activating before being deactivated
      setTimeout(() => {
        stompClient.activate();
      }, 250);

    },
    disconnectStomp: function(){
      //We call this to close the WSS connection on component unmount (tab/browser is closing)
      stompClient.deactivate();
    },
    makeOrder: function(customer, order, adding){
      //Sending an order to the backend for processing
      let orderMessage = {
        "name": customer,
        "sku": order,
        "adding": adding
      }
      stompClient.publish({destination: '/sm/ordering', body: JSON.stringify(orderMessage)});
    },
    getInitialOrders: function(){
      //This is called to fetch initial state of orders
      return fetch(apiServer + "/orders")
        .then(response => response.json())
        .then(data => {
          if(data.OK){
            var orders = JSON.parse(data.data);
            return orders;
          }
          else{
            alert(data.status);
            return ({
              'Bob': [],
              'Alice':[],
              'Tommy':[],
              'Jenny':[]
            });
          }
        })
        .catch(error => {
          console.error(error);
          return ({
            'Bob': [],
            'Alice':[],
            'Tommy':[],
            'Jenny':[]
          });
        });
    },
    getInitialSuggestions: function(){
      //This is called to fetch initial state of suggestions
      return fetch(apiServer + "/suggestions")
        .then(response => response.json())
        .then(data => {
          if(data.OK){
            var suggestions = JSON.parse(data.data);
            return suggestions;
          }
          else{
            alert(data.status);
            return ({
              'Bob': [],
              'Alice':[],
              'Tommy':[],
              'Jenny':[]
            });
          }
        })
        .catch(error => {
          console.error(error);
          return ({
            'Bob': [],
            'Alice':[],
            'Tommy':[],
            'Jenny':[]
          });
        });
    }
}

export default helpers;
