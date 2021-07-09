package com.jvheaney.adidas.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.jvheaney.adidas.models.InterestNode;

/*
 * This is not used directly by this application.
 * I added this to demonstrate the interfaces a production application may have when accessing the graph.
 * In a production environment, this would be called to access interest related queries.
 */

public interface InterestNodeRepository extends Neo4jRepository<InterestNode, Long> {

}
