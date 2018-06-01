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
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.InputStreamHandle;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DocumentWriter {
    public static void main(String[] args) throws XPathExpressionException, IOException {
        run("localhost", 8000, "admin", "heng130509", DatabaseClientFactory.Authentication.DIGEST);

    }


    // main logic
    public static void run(String host, int port, String username, String
            password, DatabaseClientFactory.Authentication authType) throws XPathExpressionException, IOException {

        // Create the database client
        DatabaseClient client = DatabaseClientFactory.newClient(
                host, port, new DatabaseClientFactory.DigestAuthContext(username, password));

        // Make a document manager to work with XML files.
        XMLDocumentManager docMgr = client.newXMLDocumentManager();

        // Define a URI value for a document.
        String docId = "/book2.xml";


        // acquire the content
        String filename = "book1.xml";

        // Get the document’s content.
        FileInputStream docStream = new FileInputStream("data" + File.separator + filename);
        if (docStream == null)
            throw new IOException("Could not read document example");

        // Create a handle associated with the input stream to receive the document’s content
        InputStreamHandle handle = new InputStreamHandle(docStream);


        // write the document content
        docMgr.write(docId, handle);


        System.out.println(
                "Connected to " + host + ":" + port);

        System.out.println("Read XML document successfully");

        // release the client
        client.release();
    }

}

