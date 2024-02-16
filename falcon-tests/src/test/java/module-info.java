/*
 * Copyright (C) 2021 iofairy, <https://github.com/iofairy/falcon>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

module falcon.test {
    requires iofairy.falcon;
    requires java.sql;
    requires org.junit.jupiter.api;
    requires lombok;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;


    exports com.iofairy.test to org.junit.platform.commons;
    exports com.iofairy.test.reflect to org.junit.platform.commons;
    exports com.iofairy.test.node to org.junit.platform.commons,com.fasterxml.jackson.databind,com.fasterxml.jackson.core;
}