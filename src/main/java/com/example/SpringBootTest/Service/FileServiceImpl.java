package com.example.SpringBootTest.Service;

import com.example.SpringBootTest.DTO.CropImageDTO;
import com.example.SpringBootTest.DTO.ImageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FileServiceImpl implements FileService{
    private String uploadPath = "path/to/your/upload_image/folder/";

    public List<ImageDTO> uploadImageFiles(MultipartFile[] uploadFiles){
        List<ImageDTO> imageDTOList = new ArrayList<>();

        if(!Objects.isNull(uploadFiles)){
            for (MultipartFile multipartFile : uploadFiles){
                if(!Objects.requireNonNull(multipartFile.getContentType().startsWith("image"))){
                    log.warn("업로드한 파일이 이미지가 아닙니다.");
                }
                String originalFilename = multipartFile.getOriginalFilename();
                String filename = originalFilename.substring(originalFilename.lastIndexOf("//") + 1);
                String saveName = uploadPath + filename;

                Path savePath = Paths.get(saveName);
                try{
                    multipartFile.transferTo(savePath);

                    imageDTOList.add(ImageDTO.builder()
                    .imageName(filename)
                    .path(uploadPath)
                    .build());
                } catch (IOException e){
                    log.error("이미지 저장 에러 imgName = {}", filename);
                    e.printStackTrace();
                }
            }
        }
        return imageDTOList;
    }

    public int makeCraftImage() throws IOException, InterruptedException {
        String pythonScriptPath = "path/to/your/craft/test.py";
        String[] cmd = new String[]{
                "python",
                pythonScriptPath,
                "--trained_model=path/to/your/craft/model/craft_mlt_25k.pth",
                "--test_folder=path/to/your/test/image/folder"
        };

        Process p = Runtime.getRuntime().exec(cmd);

        // 에러 출력 확인
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String s;
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line); // Python 스크립트에서 출력되는 내용을 출력합니다.
        }
        int exitCode = p.waitFor();
        System.out.println("Craft 이미지 crop 스크립트 실행 완료. 종료 코드: " + exitCode);
        return exitCode;
    }

    public int cropImage() throws IOException, InterruptedException {
        System.out.println("pythonbuilder ");
        ProcessBuilder builder;

        String pythonScriptPath = "path/to/your/crop_image/util_file/img_crop_forResult.py";
        builder = new ProcessBuilder("python", pythonScriptPath); //python3 error

        builder.redirectErrorStream(true);
        Process p = builder.start();

        // 자식 프로세스가 종료될 때까지 기다림
        int exitval = p.waitFor();

        if(exitval !=0){
            //비정상종료
            System.out.println("비정상종료");
        }

        // 에러 출력 확인
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String s;
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line); // Python 스크립트에서 출력되는 내용을 출력합니다.
        }
        int exitCode = p.waitFor();
        System.out.println("Craft 이미지 crop 스크립트 실행 완료. 종료 코드: " + exitCode);

        return exitCode;
    }

    public int makeLmdbFiles() throws IOException, InterruptedException {
        String pythonScriptPath = "path/to/your/create_lmdb_directImage.py";
        String[] cmd = new String[]{
                "python", // 또는 "C:/Python37/python.exe"와 같이 전체 경로를 사용해 보세요.
                pythonScriptPath,
                "--inputPath",
                "path/to/your/crop_image/folder/",
                "--outputPath",
                "path/to/your/result/folder"
        };

        Process p = Runtime.getRuntime().exec(cmd);

        // 에러 출력 확인
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String s;
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line); // Python 스크립트에서 출력되는 내용을 출력합니다.
        }
        int exitCode = p.waitFor();
        System.out.println("lmdb 생성 스크립트 실행완료. 종료 코드: " + exitCode);
        return exitCode;
    }

    public int predictImage() throws IOException, InterruptedException {
        String pythonScriptPath = "path/to/your/deep-text-recognition-benchmark/test.py";
        String[] cmd = new String[]{
                "python", // 또는 "C:/Python37/python.exe"와 같이 전체 경로를 사용해 보세요.
                pythonScriptPath,
                "--evaluation",
                "--remove_space",
                "--all_case",
                "--chars_file",
                "path/to/your/chars_list.txt",
                "--eval_data",
                "path/to/your/lmdb/folder",
                "--imgH",
                "32",
                "--imgW",
                "200",
                "--batch_max_length",
                "35",
                "--workers",
                "0",
                "--batch_size",
                "256",
                "--output_channel",
                "1024",
                "--hidden_size",
                "1024",
                "--Transformation",
                "TPS",
                "--FeatureExtraction",
                "RCNN",
                "--SequenceModeling",
                "BiLSTM",
                "--Prediction",
                "CTC",
                "--saved_model",
                "path/to/your/model.pth"
        };

        Process p = Runtime.getRuntime().exec(cmd);

        // 에러 출력 확인
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String s;
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line); // Python 스크립트에서 출력되는 내용을 출력합니다.
        }
        int exitCode = p.waitFor();
        System.out.println("predict 스크립트 실행완료. 종료 코드: " + exitCode);
        return exitCode;
    }
}
