/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.lucene;

import java.io.File;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.processor.lucene.support.Hits;
import org.apache.camel.support.DefaultProducer;
import org.apache.lucene.analysis.Analyzer;

public class LuceneQueryProducer extends DefaultProducer {
    LuceneConfiguration config;
    LuceneSearcher searcher;
    Analyzer analyzer;
    File indexDirectory;
    int maxNumberOfHits;
    int totalHitsThreshold;

    public LuceneQueryProducer(Endpoint endpoint, LuceneConfiguration config) {
        super(endpoint);
        this.config = config;
        indexDirectory = config.getIndexDir();
        analyzer = config.getAnalyzer();
        maxNumberOfHits = config.getMaxHits();
    }

    @Override
    public void doStart() throws Exception {
        searcher = new LuceneSearcher();
        super.doStart();
    }

    @Override
    public void doStop() throws Exception {
        searcher.close();
        super.doStop();
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Hits hits;

        String phrase = exchange.getIn().getHeader(LuceneConstants.HEADER_QUERY, String.class);
        String returnLuceneDocs = exchange.getIn().getHeader(LuceneConstants.HEADER_RETURN_LUCENE_DOCS, String.class);
        boolean isReturnLuceneDocs = returnLuceneDocs != null && returnLuceneDocs.equalsIgnoreCase("true");

        if (phrase != null) {
            searcher.open(indexDirectory, analyzer);
            hits = searcher.search(phrase, maxNumberOfHits, totalHitsThreshold, isReturnLuceneDocs);
        } else {
            throw new IllegalArgumentException(
                    "SearchPhrase for LucenePhraseQuerySearcher not set. Set the Header value: QUERY");
        }

        exchange.getIn().setBody(hits);
    }

    public LuceneConfiguration getConfig() {
        return config;
    }

    public void setConfig(LuceneConfiguration config) {
        this.config = config;
    }

}
