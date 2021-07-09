<h1>adidas B2B Demonstration</h1>

<p>
    This is a B2B full stack demonstration I put together for adidas to showcase the power behind data, relationships between data, and graph databases.
</p>
<img width="1440" alt="Screen Shot 2021-07-08 at 9 50 24 PM" src="https://user-images.githubusercontent.com/40678238/125011478-8c33e880-e036-11eb-8178-b05064602acd.png">



<h2>The Stack</h2>
<p>The stack is comprised of:</p>
<ul>
  <li>React for the frontend.</li>
  <li>Spring-Boot for the backend.</li>
  <li>Neo4J as the graph database.</li>
</ul>

<p>It uses the following technologies:</p>
<ul>
  <li>WebRTC, Stomp, SockJS</li>
  <li>REST Endpoints</li>
  <li>Cypher Querying Language</li>
</ul>

<h2>Running it Yourself</h2>
<p>You can run the demo by pulling the repository and linking your Neo4J instance to Spring-Boot in <code>application.properties</code>. You'll have to add the Spring-Boot endpoint in <code>Helpers.js</code> in the front-end. There are CSV files included with the test nodes, you will have to form your own relationships with the nodes (that's the fun part, afterall).</p>

<h2>Synopsis</h2>
<p><i>Taken from <code>App.js</code>.</i></p>
<p>This website demonstrates a simple, yet effective, approach to building B2B customer engagement. By matching keywords to customers, products, and stores, we are able to anticipate what our customers may want to purchase from us and proactively reach out to them with a personalized call-to-action.</p>

<p>This system uses a graph database to identify, manipulate, and understand relationships between customers, products and stores. The stack is built on React and Spring-Boot, leveraging WebRTC and RESTful services to facilitate frontend interactions. The database, and really the star of the show, is Neo4J.</p>

<p>You can see the suggestion query in action by adding or removing shoes from the orders column. The suggestion query uses relationships between customers and interests, customers and stores, stores and stores, stores and shoes, stores and interests, customers and shoes, and shoes and interests. Many more relationships can be added, and with additional data and filtering, a really strong suggestion algorithm can be created.</p>

<p>I invite you to try the system and watch it in action. You can click on each customer for a persona about them and information about the store they represent with keyword highlighting. The highlighting indicates important relationships that have been, or could be, added in the graph database. The same information and highlighting is done for each shoe. Add and remove shoes and watch as the suggestions change.</p>
