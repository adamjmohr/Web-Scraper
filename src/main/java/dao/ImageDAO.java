package dao;

import entity.Image;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Adam Mohr
 */
public class ImageDAO extends GenericDAO<Image>{
    
    public ImageDAO() {
        super(Image.class);
    }
    
    public List<Image> findAll() {
        return findResults("Image.findAll", null);
    }
    
    public Image findById( int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        
        return findResult( "Image.findById", map);
    }
    
    public List<Image> findByFeedId(int feedId) {
        Map<String, Object> map = new HashMap<>();
        map.put("feedid", feedId);
        
        return findResults( "Image.findByFeedId", map);
    }
    
    public List<Image> findByName(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        
        return findResults( "Image.findByName", map);
    }
    
    public Image findByPath(String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("path", path);
        
        return findResult( "Image.findByPath", map);
    }
    
    public List<Image> findByDate(Date date) {
        Map<String, Object> map = new HashMap<>();
        map.put("date", date);
        
        return findResults( "Image.findByDate", map);
    }
    
    public List<Image> findContaining(String search) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", search);
        
        return findResults( "Image.findContaining", map);
    }
    
    
}
