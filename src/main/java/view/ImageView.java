package view;

import common.FileUtility;
import entity.Feed;
import entity.Image;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.ImageLogic;
import scraper.Post;
import scraper.Scraper;
import scraper.Sort;

/**
 * @author Adam Mohr
 */

@WebServlet(name = "ImageView", urlPatterns = {"/ImageView"})
public class ImageView extends HttpServlet {
    
    private ImageLogic iLogic;
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request request request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Image> images = iLogic.getAll();
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //display images
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Images</title>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"style/ImageView.css\">");
            out.println("</head>");
            out.println("<body>");
            
            images.stream().filter((img) -> (img != null)).map((img) -> {
                out.println("<div class=\"imageContainer\">");
                return img;
            }).map((img) -> {
                out.println("<img class=\"imageThumb\" src=\"image/" + FileUtility.getFileName(img.getPath()) + "\"/>");
                return img;
            }).forEachOrdered((_item) -> {
                out.println("</div>");
            });

            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //create a directory
        //Windows 10 for me 
        //FileUtility.createDirectory(System.getProperty("user.home") + "/Documents/Reddit Images/");
        //Windows 8 for Shariar
        FileUtility.createDirectory(System.getProperty("user.home") + "/My Documents/Reddit Images/"); 
        // Create logics
        iLogic = new ImageLogic();
        //wallpaper feed
        Feed feed = new Feed(3);
        //create a new scraper
        Scraper scrap = new Scraper();
        //authenticate and set up a page for wallpaper subreddit with 12 posts soreted by HOT order
        scrap.authenticate().buildRedditPagesConfig("Wallpaper", 12, Sort.HOT);

        //create a lambda that accepts post
        Consumer<Post> saveImage = (Post post) -> {
            //if post is an image and SFW
            if (post.isImage() && !post.isOver18()) {
                //get the path for the image which is unique
                String path = post.getUrl();
                //check for duplicates in database
                if (iLogic.getImageWithPath(path) == null) {
                    //generate HTML code
                    Map<String, String[]> parameterMap = new HashMap<>();
                    parameterMap.put(ImageLogic.NAME, new String[]{post.getTitle()});
                    parameterMap.put(ImageLogic.PATH, new String[]{path});
                    parameterMap.put(ImageLogic.FEED_ID, new String[]{"3"}); //wallpaper feed
                    parameterMap.put(ImageLogic.DATE, new String[]{post.getDate().toString()});
                    //create img to insert
                    Image image = iLogic.createEntity(parameterMap);
                    image.setFeedid(feed);
                    //save it in img directory
                    FileUtility.downloadAndSaveFile(path, System.getProperty("user.home") + "/My Documents/Reddit Images/");
                    //add to database
                    iLogic.add(image);
                }
            }
        };

        //get the next page once and save the images.
        scrap.requestNextPage().proccessNextPage(saveImage);

        log("GET");
        processRequest(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log("POST");
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log("PUT");
        doPost(req, resp);
    }

    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log("DELETE");
        doPost(req, resp);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "ImageView webscrapper for reddit";
    }

    private static final boolean DEBUG = true;

    @Override
    public void log( String msg) {
        if(DEBUG){
            String message = String.format( "[%s] %s", getClass().getSimpleName(), msg);
            getServletContext().log( message);
        }
    }

    @Override
    public void log( String msg, Throwable t) {
        String message = String.format( "[%s] %s", getClass().getSimpleName(), msg);
        getServletContext().log( message, t);
    }
}
