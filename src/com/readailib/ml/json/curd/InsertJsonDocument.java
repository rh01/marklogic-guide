/*
 * Copyright 2018 @rh01 http://github.com/rh01
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.readailib.ml.json.curd;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.io.InputStreamHandle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class InsertJsonDocument {

    public static void main(String[] args) throws IOException {
        System.out.println("hello marklogic");
        run("localhost", 8000, "admin", "heng130509", DatabaseClientFactory.Authentication.DIGEST);
    }

    private static void run(String host, int port, String username, String
            password, DatabaseClientFactory.Authentication authType) throws IOException {
        // Create the database client
        DatabaseClient client = DatabaseClientFactory.newClient(
                host, port, new DatabaseClientFactory.DigestAuthContext(username, password));

        // create a manager for JSON documents
        JSONDocumentManager docMgr = client.newJSONDocumentManager();

        String filename = "example.json";

        // Get the document’s content.
        FileInputStream docStream = new FileInputStream("data" + File.separator + filename);
        if (docStream == null)
            throw new IOException("Could not read document example");

        // create a handle on the content
        InputStreamHandle handle = new InputStreamHandle(docStream);

        // write the document content
        docMgr.write("/example/flipper.json", handle);

        //
        System.out.println("insert Json file successful.");
        client.release();

    }

}
