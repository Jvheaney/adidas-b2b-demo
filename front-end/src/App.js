import Navbar from './components/Navbar';
import Table from './components/Table';
import Container from './elements/Container';
import Text from './elements/Text';

import helpers from './utilities/Helpers';

function App() {
  return (
    <div>
      <Navbar />
      <Container>
        <Text><h3>Welcome to the adidas B2B software demo.</h3></Text>
        <Text>
          This website demonstrates a simple, yet effective, approach to building B2B customer engagement.
          By matching keywords to customers, products, and stores, we are able to anticipate what our customers may want to purchase from us and proactively reach out to them with a personalized call-to-action.
        </Text>
        <Text>
          This system uses a graph database to identify, manipulate, and understand relationships between customers, products and stores.
          The stack is built on React and Spring-Boot, leveraging WebRTC and RESTful services to facilitate frontend interactions. The database, and really the star of the show, is Neo4J.
        </Text>
        <Text>
          You can see the suggestion query in action by adding or removing shoes from the orders column. The suggestion query uses relationships between customers and interests, customers and stores, stores and stores, stores and shoes, stores and interests, customers and shoes, and shoes and interests.
          Many more relationships can be added, and with additional data and filtering, a really strong suggestion algorithm can be created.
        </Text>
        <Text>
          I invite you to try the system and watch it in action. You can click on each customer for a persona about them and information about the store they represent with keyword highlighting. The highlighting indicates important relationships that have been, or could be, added in the graph database.
          The same information and highlighting is done for each shoe. Add and remove shoes and watch as the suggestions change.
        </Text>
        <Table />
      </Container>
    </div>
  );
}

export default App;
