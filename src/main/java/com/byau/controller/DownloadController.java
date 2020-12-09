/*     */
package com.byau.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/down"})
public class DownloadController {
    @RequestMapping({"/download"})
    public ResponseEntity<byte[]> downloadWhiteListTmp(@RequestParam("fileName") String fileName, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        fileName = fileName + ".xls";
        ServletContext servletContext = request.getServletContext();
        String realPath = servletContext.getRealPath("/download/" + fileName);
        File file = new File(realPath);
        byte[] body = null;
        ResponseEntity<byte[]> response = null;
        try {
            InputStream in = new FileInputStream(new File(realPath));
            body = new byte[in.available()];
            in.read(body);
            fileName = new String(fileName.getBytes("gbk"), "iso8859-1");
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            HttpStatus statusCode = HttpStatus.OK;
            response = new ResponseEntity(body, (MultiValueMap) headers, statusCode);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
