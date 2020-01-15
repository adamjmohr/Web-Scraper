package logic;

import dao.FeedDAO;
import entity.Feed;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Adam Mohr
 */
public class FeedLogic extends GenericLogic<Feed, FeedDAO> {

    public static final String ID = "id";
    public static final String PATH = "path";
    public static final String TYPE = "type";
    public static final String NAME = "name";
    public static final String HOST_ID = "hostId";
    
    public FeedLogic() {
        super(new FeedDAO());
    }
    
    @Override
    public List<Feed> getAll() {
        return get(() -> dao().findAll());
    }

    @Override
    public Feed getWithId(int id) {
        return get(() -> dao().findById(id));
    }
    
    public List<Feed> getFeedsWithHostID(int hostId) {
        return get(() -> dao().findByHostId(hostId));
    }
    
    public Feed getFeedWithPath(String path) {
         return get(() -> dao().findByPath(path));
    }
    
    public List<Feed> getFeedsWithType(String type) {
        return get(() -> dao().findByType(type));
    }
    
    public List<Feed> getFeedsWithName(String name) {
        return get(() -> dao().findByName(name));
    }
    
    @Override
    public List<Feed> search(String search) {
        return get(() -> dao().findContaining(search));
    }
    
    @Override
    public Feed createEntity(Map<String, String[]> parameterMap) {
        Feed feed = new Feed();
        
        if(parameterMap.containsKey(ID)){
            feed.setId(Integer.parseInt(parameterMap.get(ID)[0]));
        }
        
        if (parameterMap.containsKey(PATH) && parameterMap.get(PATH) != null) {
            String path = parameterMap.get(PATH)[0];
            if (path.isEmpty() || !path.contains(".json")) {
                throw new RuntimeException("Invalid path.");
            }
        } else {
            throw new RuntimeException("Path name must unique.");
        }
        
        if (parameterMap.get(TYPE) == null || parameterMap.get(PATH) == null || parameterMap.get(NAME) == null) {
             throw new RuntimeException("All fields must not be empty.");
        }
        
        feed.setName(parameterMap.get(NAME)[0]);
        feed.setType(parameterMap.get(TYPE)[0]);
        feed.setPath(parameterMap.get(PATH)[0]);
        
        return feed;
    }
    
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("ID", "Path", "Type", "Name", "HostID");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ID, PATH, TYPE, NAME, HOST_ID);
    }

    @Override
    public List<?> extractDataAsList(Feed e) {
        return Arrays.asList(e.getId(), e.getPath(), e.getType(), e.getName(), e.getHostid());
    }  
    
}
