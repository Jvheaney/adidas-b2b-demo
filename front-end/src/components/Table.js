import React from 'react';
import Modal from 'react-modal';

import './css/Table.css';
import bobAvatar from '../assets/images/bob.png';
import aliceAvatar from '../assets/images/alice.png';
import tommyAvatar from '../assets/images/tommy.png';
import jennyAvatar from '../assets/images/jenny.png';

import ShoeCard from './ShoeCard';
import helpers from '../utilities/Helpers';
import SHOES from '../assets/static/Shoes';
import COPY from '../assets/static/Copy';
import STOCK from '../assets/static/Stock';

const customStyles = {
  content: {
    top: '50%',
    left: '50%',
    right: 'auto',
    bottom: 'auto',
    marginRight: '-50%',
    transform: 'translate(-50%, -50%)',
    minWidth: 250,
    minHeight: 500,
    maxHeight: '80%',
    maxWidth: '70%'
  },
};

let dataNameRef, companyNameRef; //References
let dataName, modalType; //Manipulating data

Modal.setAppElement('body');

class Table extends React.Component {

  componentDidMount() {
    //Calling our helpers utility script to fetch initial state and connect stomp.
    //We are passing in this context inorder to manipulate the state from the onConnect in the stomp method.
    helpers.getInitialOrders().then(result => this.setState({orders: result}));
    helpers.getInitialSuggestions().then(result => this.setState({suggestions: result}));
    helpers.connectStomp({pass: this});
  }

  componentWillUnmount() {
    //Closing the stomp WSS connection on browser/tab close
    helpers.disconnectStomp();
  }

  state = {
    orders: {},
    suggestions: {},
    modalIsOpen: false,
    setIsOpen: false

  }

  makeBold = (copy, keywords) => {
    //Making select keywords bolded in a body of text
    for(var i = 0; i<keywords.length; i++){
      var re = new RegExp("\\b" + keywords[i] + "\\b", 'g');
      copy = copy.replace(re, '<b>'+ keywords[i] + '</b>')
    }
    return copy;
  }

  renderOrders = (customer, inModal) => {
    //Checking if promise has resolved.
    if (Object.keys(this.state.orders) && Object.keys(this.state.orders).length > 0){
      //It has resolved, lets render the orders for this specific customer
      return this.state.orders[customer].map((order) => {
          return(<ShoeCard key={customer + "_" + order} deleteShoe={() => this.deleteShoe(customer, order, false)} openModal={(inModal)?()=>{}:() => this.openModal(order, 2)} sku={order} />)
      })
    }
    else{
      //Promise hasn't resolved yet.
      return(<div></div>)
    }
  }

  renderSuggestions = (customer, inModal) => {
    //Checking if promise has resolved.
    if (Object.keys(this.state.orders) && Object.keys(this.state.orders).length > 0 && Object.keys(this.state.suggestions) && Object.keys(this.state.suggestions).length > 0){
      //It has resolved, lets render the suggestions for this specific customer
      return this.state.suggestions[customer].filter((order) => {
        //We are filtering out products that are in the orders and suggestions simultaneously.
        //Once a new suggestion is sent from the server to the client this is redundant,
        //but this is imperative to the user experience for the ~200ms it takes for the suggestions to be generated and sent.
        return (this.state.orders[customer].indexOf(order) === -1);
      }).map((order) => {
          return(<ShoeCard key={customer + "_" + order} addShoe={() => this.addShoe(customer, order, true)} openModal={(inModal)?()=>{}:() => this.openModal(order, 2)} sku={order} />)
      })
    }
    else{
      //Promise hasn't resolved yet.
      return(<div></div>)
    }
  }

  renderStock = (customer) => {
    //We are rendering all the stock for a specific customer.
    return STOCK['stock'].filter((item) => {
      //We filter items that have already been added to the customer's order section.
      return (this.state.orders[customer].indexOf(item) === -1);
    }).map((item) => {
      return(<ShoeCard key={"stock_" + item} addShoe={() => this.addShoe(customer, item, true)} sku={item} />)
    })
  }

  deleteShoe = (customer, order, adding) => {
    //Removing a shoe from the orders for a customer.
    var ordersObj = this.state.orders;
    ordersObj[customer].splice(this.state.orders[customer].indexOf(order),1);
    this.setState({
      orders: ordersObj
    });
    //Send update with stomp to generate new suggestions.
    helpers.makeOrder(customer, order, adding);
  }

  addShoe = (customer, order, adding) => {
    //Adding a shoe from the orders for a customer.
    var ordersObj = this.state.orders;
    ordersObj[customer].push(order);
    this.setState({
      orders: ordersObj
    })
    //Send update with stomp to generate new suggestions.
    helpers.makeOrder(customer, order, adding);
  }

  openModal = (name, type) => {
    //Open a modal and set default state for dataName and modalType.
    //These are used to decide what information to display in the modal.
    //Reusing the single modal component reduces filesize of the project and greatly reduces boilerplate.
    dataName = name;
    modalType = type;
    this.setState({
      modalIsOpen: true
    })
  }

  afterOpenModal = () => {
    //Depending on the modalType, we set the names for the modal.
    if(modalType === 2){
      //This indicates it's about shoes.
      dataNameRef.innerHTML=SHOES[dataName]['name']; //Get name from Shoes
    }
    else if (modalType === 0){
      //This indicates it's about a customer/company.
      dataNameRef.innerHTML=dataName;
      companyNameRef.innerHTML= COPY['customers'][dataName]['company'];
    }
    else{
      //If the modalType is 1, that is for orders.
      dataNameRef.innerHTML=dataName;
    }

  }

  closeModal = () => {
    this.setState({
      modalIsOpen: false
    })
  }

render() {
  return (
    <div>
      <table>
        <thead>
          <tr>
            <td className="CustomerColumn">
              Customer
            </td>
            <td className="OrdersColumn">
              Orders
            </td>
            <td className="SuggestionsColumn">
              Suggestions
            </td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>
              <div onClick={() => this.openModal("Bob", 0)} className="CustomerInfo">
                <img src={bobAvatar} alt="Bob" className="Avatar" />
                <div className="dataNameAndCompanyContainer">
                  <div className="dataName">
                    Bob
                  </div>
                  <div className="CustomerCompany">
                    Sport Chek
                  </div>
                </div>
              </div>
            </td>
            <td className="OrdersColumn">
              <div onClick={() => this.openModal("Bob", 1)} className="AddOrder">
                   (+) Add to Order
              </div>
              <div className="Orders">
                {this.renderOrders("Bob", false)}
              </div>
            </td>
            <td className="SuggestionsColumn">
              <div className="Orders">
                {this.renderSuggestions("Bob", false)}
              </div>
            </td>
          </tr>
          <tr>
          <td>
            <div onClick={() => this.openModal("Alice", 0)} className="CustomerInfo">
              <img src={aliceAvatar} alt="Alice" className="Avatar" />
              <div className="dataNameAndCompanyContainer">
                <div className="dataName">
                  Alice
                </div>
                <div className="CustomerCompany">
                  Foot Locker
                </div>
              </div>
            </div>
          </td>
          <td className="OrdersColumn">
            <div onClick={() => this.openModal("Alice", 1)} className="AddOrder">
               (+) Add to Order
            </div>
            <div className="Orders">
              {this.renderOrders("Alice", false)}
            </div>
          </td>
          <td className="SuggestionsColumn">
            <div className="Orders">
              {this.renderSuggestions("Alice", false)}
            </div>
          </td>
          </tr>
          <tr>
            <td>
              <div onClick={() => this.openModal("Tommy", 0)} className="CustomerInfo">
                <img src={tommyAvatar} alt="Tommy" className="Avatar" />
                <div className="dataNameAndCompanyContainer">
                  <div className="dataName">
                    Tommy
                  </div>
                  <div className="CustomerCompany">
                    Golf X-Treme
                  </div>
                </div>
              </div>
            </td>
            <td className="OrdersColumn">
              <div onClick={() => this.openModal("Tommy", 1)} className="AddOrder">
                (+) Add to Order
              </div>
              <div className="Orders">
                {this.renderOrders("Tommy", false)}
              </div>
            </td>
            <td className="SuggestionsColumn">
              <div className="Orders">
                {this.renderSuggestions("Tommy", false)}
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div onClick={() => this.openModal("Jenny", 0)} className="CustomerInfo">
                <img src={jennyAvatar} alt="Jenny" className="Avatar" />
                <div className="dataNameAndCompanyContainer">
                  <div className="dataName">
                    Jenny
                  </div>
                  <div className="CustomerCompany">
                    Hype Central
                  </div>
                </div>
              </div>
            </td>
            <td className="OrdersColumn">
              <div onClick={() => this.openModal("Jenny", 1)} className="AddOrder">
                 (+) Add to Order
              </div>
              <div className="Orders">
                {this.renderOrders("Jenny", false)}
              </div>
            </td>
            <td className="SuggestionsColumn">
              <div className="Orders">
                {this.renderSuggestions("Jenny", false)}
              </div>
            </td>
          </tr>
        </tbody>
      </table>
      <Modal
        isOpen={this.state.modalIsOpen}
        onAfterOpen={this.afterOpenModal}
        onRequestClose={this.closeModal}
        style={customStyles}
        contentLabel="CustomerAndOrdersModal"
        >
          <div onClick={this.closeModal} className="CloseModalButton">
            X
          </div>
          {(modalType === 1)?
            <h3><span ref={(_dataName) => (dataNameRef = _dataName)}></span>'s Order</h3>
            :
            <h3>About <span ref={(_dataName) => (dataNameRef = _dataName)}></span></h3>}
          {(modalType === 0)?
            <div>
              <p dangerouslySetInnerHTML={(dataName !== undefined)?{__html: this.makeBold(COPY['customers'][dataName]['bio'],COPY['customers'][dataName]['bioKeywords'])}:{__html: ""}}>
              </p>
              <h4>Works at: <span ref={(_companyNameRef) => (companyNameRef = _companyNameRef)} className="Bold"></span></h4>
              <p dangerouslySetInnerHTML={(dataName !== undefined)?{__html: this.makeBold(COPY['customers'][dataName]['companyBio'],COPY['customers'][dataName]['companyBioKeywords'])}:{__html: ""}}>
              </p>
            </div>
            :(modalType === 1)?
            <div>
              <h4>Existing Orders</h4>
              <div className="OrderInModal">
                {(dataName !== undefined)?this.renderOrders(dataName, true):<div></div>}
              </div>
              <h4>Suggested Orders</h4>
              <div className="OrderInModal">
                {(dataName !== undefined)?this.renderSuggestions(dataName, true):<div></div>}
              </div>
              <h4>All Stock</h4>
              <div className="OrderInModal">
                {(dataName !== undefined)?this.renderStock(dataName):<div></div>}
              </div>
            </div>
            :
            <div>
              <div className="ModalShoeHero">
                {(dataName !== undefined)?<img src={SHOES[dataName]['image']} alt={SHOES[dataName]['name']} className="ShoeHero" />:<div></div>}
              </div>
              <p dangerouslySetInnerHTML={(dataName !== undefined)?{__html: this.makeBold(COPY['shoes'][dataName]['description'],COPY['shoes'][dataName]['keywords'])}:{__html: ""}}>
              </p>
            </div>
          }
        </Modal>
    </div>
  );
}
}

export default Table;
