package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adam Mohr
 */
@WebServlet(name = "image", urlPatterns = {"/image/*"})
public class ImageCDN extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // source: https://stackoverflow.com/questions/8623709/output-an-image-file-from-a-servlet/8623747#8623747
        
        ServletContext cntx = req.getServletContext();
        // Get the absolute path of the image
        String path = req.getPathInfo().substring(1);
        String filename = System.getProperty("user.home")+"/My Documents/Reddit Images/" + path;
        log("ImageCDN " + filename);
        // retrieve mimeType dynamically
        String mime = cntx.getMimeType(filename);
        if (mime == null) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        resp.setContentType(mime);
        File file = new File(filename);
        resp.setContentLength((int) file.length());

        FileInputStream in = new FileInputStream(file);
        OutputStream out = resp.getOutputStream();

        // Copy the contents of the file to the output stream
        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }

    }
}
