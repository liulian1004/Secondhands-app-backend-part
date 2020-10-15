/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Barry Wang, Xin Li
 * Email: wyz1307@gmail.com, helloimlixin@gmail.com
 */

package secondHandMarket.service;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Service
public class FirebaseService {
    @PostConstruct
    public void initialize() {
        try {
//            FileInputStream serviceAccount =
//                    new FileInputStream("secondehandmarket-firebase-adminsdk-ldx1h-c9d3b52b53.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials
                            .fromStream(getClass().getClassLoader()
                                    .getResourceAsStream(
                                            "secondehandmarket-firebase-adminsdk-ldx1h-c9d3b52b53.json")))
                    .setDatabaseUrl("https://secondehandmarket.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
