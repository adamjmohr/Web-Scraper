package dao;

import entity.Feed;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Adam Mohr
 */
public class FeedDAO extends GenericDAO<Feed>{
    
    public FeedDAO() {
        super(Feed.class);
    }

    public List<Feed> findAll() {
        return findResults("Feed.findAll", null);
    }
    
    public Feed findById( int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        
        return findResult( "Feed.findById", map);
    }
    
    public List<Feed> findByHostId(int hostId) {
        Map<String, Object> map = new HashMap<>();
        map.put("hostId", hostId);
        
        return findResults( "Feed.findByHostId", map);
    }
    
    public Feed findByPath(String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("path", path);
        
        return findResult( "Feed.findByPath", map);
    }
    
    public List<Feed> findByType(String type) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        
        return findResults( "Feed.findByType", map);
    }
    
    public List<Feed> findByName(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        
        return findResults( "Feed.findByName", map);
    }
    
    public List<Feed> findContaining(String search) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", search);
        
        return findResults( "Feed.findContaining", map);
    }
    
     
     
}
