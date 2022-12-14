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
package org.apache.camel.component.quartz;

import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import static org.junit.jupiter.api.Assertions.assertSame;

public class QuartzComponentTest extends BaseQuartzTest {

    @Test
    public void testQuartzComponentCustomScheduler() throws Exception {
        QuartzComponent comp = new QuartzComponent();
        comp.setCamelContext(context);

        SchedulerFactory fac = new StdSchedulerFactory();
        comp.setSchedulerFactory(fac);
        assertSame(fac, comp.getSchedulerFactory());

        Scheduler sch = fac.getScheduler();
        comp.setScheduler(sch);
        assertSame(sch, comp.getScheduler());

        comp.start();
        comp.stop();
    }

    @Test
    public void testQuartzComponent() {
        QuartzComponent comp = new QuartzComponent(context);
        comp.start();
        comp.stop();
    }

}
