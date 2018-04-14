package com.emcloud.uaa.web;

import com.emcloud.uaa.EmCloudUaaApp;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmCloudUaaApp.class)
public class Ayou {
    public static void main(String[] args) {
        String baba="01";
        String zaza="0102";

       System.out.println(zaza.substring(0,2)+"  游洪 游洪游洪游洪游洪      wowoowowowowowoowowowo");
    }
}
