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
package com.readailib.ml;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.TextDocumentManager;
import com.marklogic.client.io.StringHandle;

import java.io.IOException;

public class ClientCreator {

    public static void main(String[] args) throws IOException {
        System.out.println("hello marklogic");
        run("localhost", 8000,"admin", "heng130509", DatabaseClientFactory.Authentication.DIGEST);
    }


    // main logic
    public static void run(String host, int port, String username, String
            password, DatabaseClientFactory.Authentication authType) {

        // Create the database client
        DatabaseClient client = DatabaseClientFactory.newClient(
                host, port, new DatabaseClientFactory.DigestAuthContext(username, password));

        // Make a document manager to work with text files.
        TextDocumentManager docMgr = client.newTextDocumentManager();

        // Define a URI value for a document.
        String docId = "/example/books.txt";

        // Create a handle to hold string content.
        StringHandle handle = new StringHandle();

        // Give the handle some content
        handle.set("A simple text document");

        // Write the document to the database with URI from docId
        // and content from handle
        docMgr.write(docId, handle);



        System.out.println(
                "Connected to "+host+":"+port);

        System.out.println("Insert document successfully");

        // release the client
        client.release();
    }

}