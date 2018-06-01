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
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import org.w3c.dom.Document;


public class DocumentMetaDataReader {
    public static void main(String[] args) {
        run("localhost", 8000, "admin", "heng130509", DatabaseClientFactory.Authentication.DIGEST);

    }

    // main logic
    public static void run(String host, int port, String username, String
            password, DatabaseClientFactory.Authentication authType) {

        // Create a client with username and password
        DatabaseClient client = DatabaseClientFactory.newClient(
                host, port, new DatabaseClientFactory.DigestAuthContext(username, password));

        // New a XML Manager  or make a manager work for xml document
        XMLDocumentManager docMgr = client.newXMLDocumentManager();

        // Create a metaDataHandler, receive the document metadata
        DocumentMetadataHandle metadataHandle = new DocumentMetadataHandle();

        // Create a Domhandler object, to handle content of Document
        DOMHandle contentHandle = new DOMHandle();


        // docId is a variable containing a document URI.
        String docId = "/book1.xml";

        // Read only the metadata into a handle
        // docMgr.readMetadata(docId, metadataHandle);

        // =======YOU CAN ALSO USE THE FOLLOWING ONE.=========
        // read metadata and content
        docMgr.read(docId, metadataHandle, contentHandle);


        // add some meta data to document
        metadataHandle.getCollections().addAll("products", "real-estate");
        metadataHandle.getPermissions().add("app-user", DocumentMetadataHandle.Capability.UPDATE, DocumentMetadataHandle.Capability.READ);
        metadataHandle.getProperties().put("reviewed", true);
        metadataHandle.setQuality(1);
        metadataHandle.getMetadataValues().add("key1", "value1");

        // Access the metadata,use get method
        // Get the set of collections the document belongs to and put in array.
        DocumentMetadataHandle.DocumentCollections collections = metadataHandle.getCollections();

        // access the document content
        Document document = contentHandle.get();

        String collFirst = collections.toArray(new String[collections.size()])[0];
        String rootName = document.getDocumentElement().getTagName();
        System.out.println("Read "+docId +
                " metadata and content in the '"+ collFirst+"' collection with the <"+rootName+"/> root element");



        //

        // Release the connection resources
        client.release();


    }
}
