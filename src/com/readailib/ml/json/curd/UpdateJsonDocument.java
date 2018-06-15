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

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.io.JacksonHandle;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.io.marker.JSONReadHandle;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.List;

public class UpdateJsonDocument {
    public static void main(String[] args) throws IOException {
        run("localhost", 8000,"admin", "heng130509", DatabaseClientFactory.Authentication.DIGEST);

    }

    private static void run(String host, int port, String username, String
            password, DatabaseClientFactory.Authentication authType) throws IOException {
        // Create the database client
        DatabaseClient client = DatabaseClientFactory.newClient(
                host, port, new DatabaseClientFactory.DigestAuthContext(username, password));

        // Make a document manager to work with XML files.
        JSONDocumentManager docMgr = client.newJSONDocumentManager();

        // create a handle to receive the document content
        //StringHandle handle = new StringHandle();

        JSONReadHandle handle = new JacksonHandle();


        //read the document content
        docMgr.read("/example/flipper.json", handle);

        // read the specified item
        JsonNode root = ((JacksonHandle) handle).get();
        ((JacksonHandle) handle).set(root);


        System.out.println(root);
        client.release();

    }
}
