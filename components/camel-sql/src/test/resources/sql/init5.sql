--
-- Licensed to the Apache Software Foundation (ASF) under one or more
-- contributor license agreements.  See the NOTICE file distributed with
-- this work for additional information regarding copyright ownership.
-- The ASF licenses this file to You under the Apache License, Version 2.0
-- (the "License"); you may not use this file except in compliance with
-- the License.  You may obtain a copy of the License at
--
--      http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

CREATE TABLE aggregationRepo5 (
    id varchar(255) NOT NULL,
    exchange blob NOT NULL,
    version bigint NOT NULL,
    constraint aggregationRepo1_pk PRIMARY KEY (id)
);
CREATE TABLE aggregationRepo5_completed (
    id varchar(255) NOT NULL,
    exchange blob NOT NULL,
    version bigint NOT NULL,
    instance_id varchar(255),
    constraint aggregationRepo1_completed_pk PRIMARY KEY (id)
);