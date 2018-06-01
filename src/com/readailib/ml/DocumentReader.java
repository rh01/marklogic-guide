/*
 * Copyright 2018 ShenHengheng
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
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;


public class DocumentReader {

    public static void main(String[] args) throws XPathExpressionException {

        run("localhost", 8000, "admin", "heng130509", DatabaseClientFactory.Authentication.DIGEST);

    }

    // main logic
    public static void run(String host, int port, String username, String
            password, DatabaseClientFactory.Authentication authType) throws XPathExpressionException {

        // Create the database client
        DatabaseClient client = DatabaseClientFactory.newClient(
                host, port, new DatabaseClientFactory.DigestAuthContext(username, password));

        // Make a document manager to work with XML files.
        XMLDocumentManager docMgr = client.newXMLDocumentManager();

        // Define a URI value for a document.
        String docId = "/books.xml";

        // POJO format
        Document doc = docMgr.readAs(docId, Document.class);

        System.out.println("read document " + docId + " which has element root <" + doc.getDocumentElement().getTagName() + ">");



        String textContent1 = doc.getDocumentElement().getTextContent();
        System.out.println("Get " + docId + ", Content is " + textContent1);


        /*NodeList NLs = doc.getElementsByTagName("title");

        for (int i = 0; i < NLs.getLength(); i++) {
            Node item = NLs.item(i);
            // return <title> value
            String textContent = item.getTextContent();
            System.out.println(textContent);
        }*/


        System.out.println(
                "Connected to " + host + ":" + port);

        System.out.println("Read XML document successfully");

        // release the client
        client.release();
    }
}

