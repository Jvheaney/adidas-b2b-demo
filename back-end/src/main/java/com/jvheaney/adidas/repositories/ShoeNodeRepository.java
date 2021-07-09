package com.jvheaney.adidas.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.jvheaney.adidas.models.ShoeNode;

/*
 * This is not used directly by this application.
 * I added this to demonstrate the interfaces a production application may have when accessing the graph.
 * In a production environment, this would be called to access shoe related queries.
 */

public interface ShoeNodeRepository extends Neo4jRepository<ShoeNode, Long> {

}
