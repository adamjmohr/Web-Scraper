package logic;

import dao.ImageDAO;
import entity.Image;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Adam Mohr
 */
public class ImageLogic extends GenericLogic<Image, ImageDAO> {

    public static final String ID = "id";
    public static final String PATH = "path";
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String FEED_ID = "feedId";
    
    public ImageLogic() {
        super(new ImageDAO());
    }
    
    @Override
    public List<Image> getAll() {
        return get(() -> dao().findAll());
    }

    @Override
    public Image getWithId(int id) {
        return get(() -> dao().findById(id));
    }
    
    public List<Image> getImagesWithFeedId(int feedId) {
        return get(() -> dao().findByFeedId(feedId));
    }
    
    public List<Image> getImagesWithName(String name) {
        return get(() -> dao().findByName(name));
    }
    
    public Image getImageWithPath(String path) {
        return get(() -> dao().findByPath(path));
    }
    
    public List<Image> getImagesWithDate(Date date) {
        return get(() -> dao().findByDate(date));
    }
    
    @Override
    public List<Image> search(String search) {
        return get(() -> dao().findContaining(search));
    }
    
    @Override
    public Image createEntity(Map<String, String[]> parameterMap) {
        Image image = new Image();
        
        if(parameterMap.containsKey(ID)){
            image.setId(Integer.parseInt(parameterMap.get(ID)[0]));
        }
        image.setName(parameterMap.get(NAME)[0]);
        image.setPath(parameterMap.get(PATH)[0]);
        image.setDate(Calendar.getInstance().getTime());
        
        return image;
    }
    
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("ID", "Path", "Name", "Date", "FeedID");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ID, PATH, NAME, DATE, FEED_ID);
    }

    @Override
    public List<?> extractDataAsList(Image e) {
        return Arrays.asList(e.getId(), e.getPath(), e.getName(), e.getDate(), e.getFeedid());
    }
    
    
}
