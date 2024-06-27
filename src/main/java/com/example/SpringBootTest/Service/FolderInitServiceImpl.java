package com.example.SpringBootTest.Service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class FolderInitServiceImpl implements FolderInitService{

    @Override
    public void mainImageFolderInit() throws IOException {
        String cmd = "del /Q path/to/your/upload_image/folder/*.*";
        Process p = Runtime.getRuntime().exec("cmd /c" + cmd);

        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String l = null;
        StringBuffer sb = new StringBuffer();
        sb.append(cmd);

        while ((l = r.readLine()) != null) {
            sb.append(l);
            sb.append("\n");
        }
    }

    @Override
    public void craftImageFolderInit() throws IOException{
        String cmd = "del /Q path/to/your/craft_image/folder/*.*";
        Process p = Runtime.getRuntime().exec("cmd /c" + cmd);

        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String l = null;
        StringBuffer sb = new StringBuffer();
        sb.append(cmd);

        while ((l = r.readLine()) != null) {
            sb.append(l);
            sb.append("\n");
        }
    }

    @Override
    public void cropImageFolderInit() throws IOException{
        String cmd = "del /Q path/to/your/crop_image/folder/*.*";
        Process p = Runtime.getRuntime().exec("cmd /c" + cmd);

        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String l = null;
        StringBuffer sb = new StringBuffer();
        sb.append(cmd);

        while ((l = r.readLine()) != null) {
            sb.append(l);
            sb.append("\n");
        }
    }
}
