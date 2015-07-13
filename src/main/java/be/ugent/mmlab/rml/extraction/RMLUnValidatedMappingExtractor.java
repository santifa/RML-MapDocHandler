package be.ugent.mmlab.rml.extraction;

import be.ugent.mmlab.rml.model.std.StdTriplesMap;
import be.ugent.mmlab.rml.model.TriplesMap;
import be.ugent.mmlab.rml.sesame.RMLSesameDataSet;
import be.ugent.mmlab.rml.vocabulary.R2RMLVocabulary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;

/**
 * *************************************************************************
 *
 * RML - Mapping Document Handler : RMLUnValidatedMappingExtractor
 *
 *
 * @author andimou
 *
 ***************************************************************************
 */
public class RMLUnValidatedMappingExtractor extends StdRMLMappingExtractor implements RMLMappingExtractor {
    
    // Log
    static final Logger log = LoggerFactory.getLogger(RMLUnValidatedMappingExtractor.class);
       
    /**
     * Construct TriplesMap objects rule. A triples map is represented by a
     * resource that references the following other resources : - It must have
     * exactly one subject map * using the rr:subjectMap property.
     *
     * @param rmlMappingGraph
     * @return
     */
    @Override
    public Map<Resource, TriplesMap> extractTriplesMapResources(
            RMLSesameDataSet rmlMappingGraph) {
        Map<Resource, TriplesMap> triplesMapResources = new HashMap<Resource, TriplesMap>();
        
        List<Statement> statements = getTriplesMapResources(rmlMappingGraph);

        triplesMapResources = putTriplesMapResources(statements, triplesMapResources);
        
        return triplesMapResources;
    }
    
    /**
     *
     * @param rmlMappingGraph
     * @return
     */
    protected List<Statement> getTriplesMapResources(RMLSesameDataSet rmlMappingGraph){
        
        URI o = rmlMappingGraph.URIref(R2RMLVocabulary.R2RML_NAMESPACE
                + R2RMLVocabulary.R2RMLTerm.TRIPLES_MAP_CLASS);
        URI p = rmlMappingGraph.URIref("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        List<Statement> statements = rmlMappingGraph.tuplePattern(null, p, o);
        return statements;
    }
    
    /**
     *
     * @param statements
     * @param triplesMapResources
     * @return
     */
    protected Map<Resource, TriplesMap> putTriplesMapResources(
            List<Statement> statements, Map<Resource, TriplesMap> triplesMapResources) {
        for (Statement s : statements) {
            triplesMapResources.put(s.getSubject(),
                    new StdTriplesMap(null, null, null, s.getSubject().stringValue()));
        }
        return triplesMapResources;
    }
}