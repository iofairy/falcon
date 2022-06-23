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

module iofairy.falcon {
    requires transitive iofairy.functional;

    exports com.iofairy.falcon.fs;
    exports com.iofairy.falcon.os;
    exports com.iofairy.falcon.reflect;
    exports com.iofairy.falcon.regex;
    exports com.iofairy.falcon.time;
    exports com.iofairy.falcon.util;
    exports com.iofairy.falcon.iterable;
    exports com.iofairy.falcon.map;
    exports com.iofairy.falcon.string;
}