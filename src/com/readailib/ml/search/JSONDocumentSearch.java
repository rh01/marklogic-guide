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

package com.readailib.ml.search;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.impl.QueryManagerImpl;
import com.marklogic.client.io.Format;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.RawQueryByExampleDefinition;

import java.io.IOException;

public class JSONDocumentSearch {
    public static void main(String[] args) throws IOException {
        run("localhost", 8000, "admin", "heng130509", DatabaseClientFactory.Authentication.DIGEST);

    }

    private static void run(String host, int port, String username, String
            password, DatabaseClientFactory.Authentication authType) throws IOException {

        // Create the database client
        DatabaseClient client = DatabaseClientFactory.newClient(
                host, port, new DatabaseClientFactory.DigestAuthContext(username, password));

        // Make a document manager to work with XML files.
        JSONDocumentManager docMgr = client.newJSONDocumentManager();

        // create a search definition
        StringHandle handle = new StringHandle(
                "{ \"$query\": { \"name\": \"edison\" } }"
        ).withFormat(Format.JSON);

        QueryManager queryMgr = client.newQueryManager();
        RawQueryByExampleDefinition query = queryMgr.newRawQueryByExampleDefinition(handle);

        // create a handle for the search results
        StringHandle resultsHandle = new StringHandle().withFormat(Format.JSON);

        // run the search
        StringHandle search = queryMgr.search(query, resultsHandle);
        System.out.println(search.get());
        client.release();

    }
}
