/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.toolkit.generator;

import javax.ws.rs.Path;

import org.apache.servicecomb.provider.pojo.RpcSchema;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.toolkit.generator.context.OasContext;
import org.apache.servicecomb.toolkit.generator.parser.ServicecombJaxrsParser;
import org.apache.servicecomb.toolkit.generator.parser.ServicecombPojoParser;
import org.apache.servicecomb.toolkit.generator.parser.ServicecombSpringmvcParser;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;

public class ServiceCombParserTest {

  @Test
  public void parse() {

    ServicecombJaxrsParser servicecombJaxrsParser = new ServicecombJaxrsParser();
    ServicecombPojoParser servicecombPojoParser = new ServicecombPojoParser();
    ServicecombSpringmvcParser servicecombSpringmvcParser = new ServicecombSpringmvcParser();

    boolean canProcess = servicecombJaxrsParser.canProcess(ServicecombJaxrs.class);
    Assert.assertTrue(canProcess);

    canProcess = servicecombJaxrsParser.canProcess(ServicecombPojo.class);
    Assert.assertFalse(canProcess);

    canProcess = servicecombSpringmvcParser.canProcess(ServicecombSpringmvc.class);
    Assert.assertTrue(canProcess);
    canProcess = servicecombSpringmvcParser.canProcess(ServicecombPojo.class);
    Assert.assertFalse(canProcess);

    canProcess = servicecombPojoParser.canProcess(ServicecombPojo.class);
    Assert.assertTrue(canProcess);
    canProcess = servicecombPojoParser.canProcess(ServicecombSpringmvc.class);
    Assert.assertFalse(canProcess);
    Assert.assertEquals(100, servicecombPojoParser.getOrder());

    OasContext pojoOasContext = new OasContext(servicecombPojoParser);
    servicecombPojoParser.parser(ServicecombPojo.class, pojoOasContext);
    Assert.assertNull(pojoOasContext.getBasePath());
  }


  @RestSchema(schemaId = "servicecombJaxrs")
  @Path("/")
  class ServicecombJaxrs {

    @Path("/path")
    public Object path() {
      return null;
    }
  }

  @RestSchema(schemaId = "servicecombSpringmvc")
  @RequestMapping
  class ServicecombSpringmvc {
  }

  @RpcSchema
  class ServicecombPojo {
    public Object path() {
      return null;
    }
  }
}
