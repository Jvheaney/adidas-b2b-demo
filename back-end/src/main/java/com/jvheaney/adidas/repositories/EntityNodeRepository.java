package com.jvheaney.adidas.repositories;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.jvheaney.adidas.models.EntityNode;

public interface EntityNodeRepository extends Neo4jRepository<EntityNode, Long> {
	
	/*
	 * This query gets customer orders by matching the name of the customer (this can be customerId in a larger system),
	 * matching that customer's relationship to a company (which "Entity" node they work at), then checks what shoes
	 * that store ordered.
	 */
	@Query("MATCH (e:Entity) " + 
			"WHERE e.name=$name " + 
			"MATCH (e)-[:WORKS_AT]->(:Entity)-[:ORDERED]->(s:Shoe) " + 
			"RETURN s.sku")
	public List<String> getCustomerOrders(@Param("name") String name);
	
	/*
	 * This is the suggestion query. It fetches user suggestions by comparing products between interests, companies, and customers. 
	 * 
	 * (1) We first fetch all shoes that have a relationships that is no more than 3 relationships away (it checks path length from starting to end node).
	 * 	This is filtering that can be adjusted as we see fit. It also ensures that these shoe nodes it's fetching are not ones this customer has already
	 * 	ordered. We wouldn't want to suggest a product they already have in stock.
	 * 
	 * (2) Next we see what companies that are similar to our customer's have ordered. We do this by checking relationships between their company and
	 * 	others in our system. We limit the path length of the similarity to 2, this ensures we only have the most similar companies coupled with the
	 * 	customer. We check what shoes that company has ordered, make sure those shoes are not ones we've ordered, and add those to the suggestions.
	 * 
	 * (3) In order to help score how much this customer's customers may like the shoe, we check to see how many interests this other company has
	 * 	similar to our customer's.
	 * 
	 * (4) To aid in this scoring, we figure out how many total interests our customer's company has.
	 * 
	 * (5) We put our shoe suggestions from (1) and (2), along with their respective scores, into a collection. The scoring is 1:1 with the path for
	 *  suggestions from (1), but suggestions from (2) may indicate market trends our customer's company might be interest in, and we want to make sure
	 *  our customers get first dibs at those, so we score those based on the similarity between the other company and our customer's.
	 *  
	 * (6) We unwind our collection of shoes and scores, order by lowest score, filter nulls, and return 4 distinct and personalized suggestions.
	 */
	@Query("MATCH (e:Entity) " +
			"WHERE e.name=$name " +
			"MATCH (c:Entity) " +
			"WHERE (e)-[:WORKS_AT]->(c) " +
			"OPTIONAL MATCH p=(c)-[:HAS|ORDERED|WORKS_AT*..3]-(s:Shoe) " + //(1)
			"WHERE NOT EXISTS((c)-[:ORDERED]->(s)) " +
			"OPTIONAL MATCH p2=(c)-[:HAS*..2]-(c2:Entity)-[:ORDERED]->(s2:Shoe) " + //(2)
			"WHERE NOT EXISTS((c)-[:ORDERED]->(s2)) " +
			"OPTIONAL MATCH (c)-[:HAS]->(i:Interest)<-[:HAS]-(c2:Entity) " + //(3)
			"OPTIONAL MATCH (c)-[:HAS]->(i2:Interest) " + //(4)
			"WITH count(i) as similarities, count(i2) as totalCompanyInterests, p, p2, s, s2 " +
			"WITH collect(DISTINCT {shoe: s, score:length(p)}) + collect(DISTINCT {shoe: s2, score:length(p2)*(1-(similarities/totalCompanyInterests))}) " +
			"as shoeSuggestions " + //and line above (5) 
			"UNWIND shoeSuggestions as final " +
			"WITH final " +
			"ORDER BY final.score " +
			"WHERE final.shoe IS NOT null " +
			"RETURN DISTINCT final.shoe.sku LIMIT 4") //(6)
	public List<String> getCustomerSuggestions(@Param("name") String name);
	
	/*
	 * This query creates the ordered relationship between a customer's company and a product.
	 */
	@Query("MATCH (e:Entity) " +
			"WHERE e.name=$name " +
			"MATCH (e)-[:WORKS_AT]->(c:Entity) " +
			"MATCH (s:Shoe) " +
			"WHERE s.sku=$sku " +
			"MERGE (c)-[:ORDERED]->(s)")
	public void placeOrder(@Param("name") String name, @Param("sku") String sku);
	
	/*
	 * This query removes the ordered relationship between a customer's company and a product.
	 */
	@Query("MATCH (e:Entity) " +
			"WHERE e.name=$name " +
			"MATCH (e)-[:WORKS_AT]->(c:Entity) " +
			"MATCH (s:Shoe) " +
			"WHERE s.sku=$sku " +
			"MATCH (c)-[r:ORDERED]->(s) " +
			"DELETE r")
	public void removeOrder(@Param("name") String name, @Param("sku") String sku);

}
