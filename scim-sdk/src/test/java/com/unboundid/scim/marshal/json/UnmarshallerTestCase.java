/*
 * Copyright 2011-2015 UnboundID Corp.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPLv2 only)
 * or the terms of the GNU Lesser General Public License (LGPLv2.1 only)
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 */

package com.unboundid.scim.marshal.json;

import com.unboundid.scim.data.BaseResource;
import com.unboundid.scim.marshal.Unmarshaller;
import com.unboundid.scim.schema.CoreSchema;
import com.unboundid.scim.schema.ResourceDescriptor;
import com.unboundid.scim.sdk.InvalidResourceException;
import com.unboundid.scim.sdk.SCIMAttribute;
import com.unboundid.scim.sdk.SCIMAttributeValue;
import com.unboundid.scim.sdk.SCIMConstants;
import com.unboundid.scim.sdk.SCIMObject;
import com.unboundid.scim.SCIMTestCase;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import java.io.InputStream;


@Test
public class UnmarshallerTestCase extends SCIMTestCase {
  /**
   * Verify that a known valid user can be read from JSON.
   *
   * @throws Exception If the test fails.
   */
  @Test
  public void testUnmarshal() throws Exception {
    final ResourceDescriptor userResourceDescriptor =
        CoreSchema.USER_DESCRIPTOR;
    InputStream testJson =
        getResource("/com/unboundid/scim/marshal/spec/core-user.json");
    final Unmarshaller unmarshaller = new JsonUnmarshaller();
    final SCIMObject o = unmarshaller.unmarshal(
        testJson, userResourceDescriptor,
        BaseResource.BASE_RESOURCE_FACTORY).getScimObject();
    assertNotNull(o);
    SCIMAttribute roles =
        o.getAttribute(SCIMConstants.SCHEMA_URI_CORE, "roles");
    assertNotNull(roles);
    assertEquals(roles.getValues().length, 3);
    assertEquals(
        roles.getValues()[0].getAttribute("value").getValue().getStringValue(),
        "Employee");
    assertEquals(
        roles.getValues()[1].getAttribute("value").getValue().getStringValue(),
        "Accounting");
    assertEquals(
        roles.getValues()[2].getAttribute("value").getValue().getStringValue(),
        "Web");
    SCIMAttribute groups =
        o.getAttribute(SCIMConstants.SCHEMA_URI_CORE, "groups");
    assertNotNull(groups);
    assertEquals(groups.getValues().length, 3);
    assertEquals(
        groups.getValues()[0].getAttribute("value").getValue().getStringValue(),
        "00300000005N2Y6AA");
    assertEquals(
     groups.getValues()[0].getAttribute("primary").getValue().getBooleanValue(),
        Boolean.TRUE);
    assertEquals(
        groups.getValues()[0].getAttribute("type").getValue().getStringValue(),
        "Tour Guides");
    assertEquals(
        groups.getValues()[1].getAttribute("value").getValue().getStringValue(),
        "00300000005N34H78");
    assertEquals(
        groups.getValues()[1].getAttribute("type").getValue().getStringValue(),
        "Employees");
    assertEquals(
        groups.getValues()[2].getAttribute("value").getValue().getStringValue(),
        "00300000005N98YT1");
    assertEquals(
        groups.getValues()[2].getAttribute("type").getValue().getStringValue(),
        "US Employees");

    SCIMAttribute x509Certificates =
        o.getAttribute(SCIMConstants.SCHEMA_URI_CORE, "x509Certificates");
    assertNotNull(x509Certificates);
    assertEquals(x509Certificates.getValues().length, 1);
    final SCIMAttributeValue binaryAttributeValue =
        x509Certificates.getValues()[0].getAttribute("value").getValue();
    assertEquals(
        binaryAttributeValue.getStringValue(),
        "MIIDQzCCAqygAwIBAgICEAAwDQYJKoZIhvcNAQEFBQAwTjELMAkGA1UEBhMCVVMx" +
        "EzARBgNVBAgMCkNhbGlmb3JuaWExFDASBgNVBAoMC2V4YW1wbGUuY29tMRQwEgYD" +
        "VQQDDAtleGFtcGxlLmNvbTAeFw0xMTEwMjIwNjI0MzFaFw0xMjEwMDQwNjI0MzFa" +
        "MH8xCzAJBgNVBAYTAlVTMRMwEQYDVQQIDApDYWxpZm9ybmlhMRQwEgYDVQQKDAtl" +
        "eGFtcGxlLmNvbTEhMB8GA1UEAwwYTXMuIEJhcmJhcmEgSiBKZW5zZW4gSUlJMSIw" +
        "IAYJKoZIhvcNAQkBFhNiamVuc2VuQGV4YW1wbGUuY29tMIIBIjANBgkqhkiG9w0B" +
        "AQEFAAOCAQ8AMIIBCgKCAQEA7Kr+Dcds/JQ5GwejJFcBIP682X3xpjis56AK02bc" +
        "1FLgzdLI8auoR+cC9/Vrh5t66HkQIOdA4unHh0AaZ4xL5PhVbXIPMB5vAPKpzz5i" +
        "PSi8xO8SL7I7SDhcBVJhqVqr3HgllEG6UClDdHO7nkLuwXq8HcISKkbT5WFTVfFZ" +
        "zidPl8HZ7DhXkZIRtJwBweq4bvm3hM1Os7UQH05ZS6cVDgweKNwdLLrT51ikSQG3" +
        "DYrl+ft781UQRIqxgwqCfXEuDiinPh0kkvIi5jivVu1Z9QiwlYEdRbLJ4zJQBmDr" +
        "SGTMYn4lRc2HgHO4DqB/bnMVorHB0CC6AV1QoFK4GPe1LwIDAQABo3sweTAJBgNV" +
        "HRMEAjAAMCwGCWCGSAGG+EIBDQQfFh1PcGVuU1NMIEdlbmVyYXRlZCBDZXJ0aWZp" +
        "Y2F0ZTAdBgNVHQ4EFgQU8pD0U0vsZIsaA16lL8En8bx0F/gwHwYDVR0jBBgwFoAU" +
        "dGeKitcaF7gnzsNwDx708kqaVt0wDQYJKoZIhvcNAQEFBQADgYEAA81SsFnOdYJt" +
        "Ng5Tcq+/ByEDrBgnusx0jloUhByPMEVkoMZ3J7j1ZgI8rAbOkNngX8+pKfTiDz1R" +
        "C4+dx8oU6Za+4NJXUjlL5CvV6BEYb1+QAEJwitTVvxB/A67g42/vzgAtoRUeDov1" +
        "+GFiBZ+GNF/cAYKcMtGcrs2i97ZkJMo=");
    binaryAttributeValue.getBinaryValue();  // Should not throw.
  }


  /**
   * Verify that JSON with missing or malformed schema is handled appropriately.
   *
   * @throws Exception If the test fails.
   */
  @Test
  public void testUnmarshalBadSchema() throws Exception {
    final ResourceDescriptor userResourceDescriptor =
        CoreSchema.USER_DESCRIPTOR;
    final Unmarshaller unmarshaller = new JsonUnmarshaller();
    InputStream testJson;
    // Test unmarshalling a JSON user entry
    testJson = getResource("/com/unboundid/scim/marshal/spec/mixed-user.json");
    try
    {
      unmarshaller.unmarshal(testJson, userResourceDescriptor,
              BaseResource.BASE_RESOURCE_FACTORY);
      fail("Expected JSONUnmarshaller to detect an ambiguous " +
              "resource representation.");
    }
    catch(InvalidResourceException e)
    {
      //expected
      System.err.println(e.getMessage());
    }
    testJson =
      getResource("/com/unboundid/scim/marshal/spec/mixed-user-malformed.json");
    try
    {
      unmarshaller.unmarshal(testJson, userResourceDescriptor,
              BaseResource.BASE_RESOURCE_FACTORY);
      fail("Expected JSONUnmarshaller to detect an ambiguous " +
              "resource representation.");
    }
    catch(InvalidResourceException e)
    {
      //expected
      System.err.println(e.getMessage());
    }

    // Try with implicit schema checking enabled
    testJson = getResource("/com/unboundid/scim/marshal/spec/mixed-user.json");
    try
    {
      System.setProperty(SCIMConstants.IMPLICIT_SCHEMA_CHECKING_PROPERTY,
                         "true");
      final SCIMObject o = unmarshaller.unmarshal(
                    testJson, userResourceDescriptor,
                    BaseResource.BASE_RESOURCE_FACTORY).getScimObject();
            assertNotNull(o);
            SCIMAttribute username =
                    o.getAttribute(SCIMConstants.SCHEMA_URI_CORE, "userName");
            assertEquals(username.getValue().getStringValue(), "babs");
            SCIMAttribute employeeNumber =
                    o.getAttribute(
                            SCIMConstants.SCHEMA_URI_ENTERPRISE_EXTENSION,
                            "employeeNumber");
            assertEquals(employeeNumber.getValue().getStringValue(), "1");
    }
    finally
    {
      System.setProperty(SCIMConstants.IMPLICIT_SCHEMA_CHECKING_PROPERTY, "");
    }
    testJson =
      getResource("/com/unboundid/scim/marshal/spec/mixed-user-malformed.json");
    try
    {
      System.setProperty(SCIMConstants.IMPLICIT_SCHEMA_CHECKING_PROPERTY,
                         "true");
      unmarshaller.unmarshal(testJson, userResourceDescriptor,
              BaseResource.BASE_RESOURCE_FACTORY);
      fail("Expected JSONUnmarshaller to fail because no schema can be found " +
                   "for attribute.");
    }
    catch(InvalidResourceException e)
    {
      //expected
      System.err.println(e.getMessage());
    }
    finally
    {
      System.setProperty(SCIMConstants.IMPLICIT_SCHEMA_CHECKING_PROPERTY, "");
    }

    // Test unmarshalling a JSON user patch with meta data
    testJson =
          getResource("/com/unboundid/scim/marshal/spec/meta-user-patch.json");
    try
    {
      unmarshaller.unmarshal(testJson, userResourceDescriptor,
              BaseResource.BASE_RESOURCE_FACTORY);
      fail("Expected JSONUnmarshaller to detect an ambiguous " +
              "resource representation.");
    }
    catch(InvalidResourceException e)
    {
      //expected
      System.err.println(e.getMessage());
    }
    // Try with implicit schema checking enabled
    testJson =
          getResource("/com/unboundid/scim/marshal/spec/meta-user-patch.json");
    try
    {
      System.setProperty(SCIMConstants.IMPLICIT_SCHEMA_CHECKING_PROPERTY,
                         "true");
      final SCIMObject o = unmarshaller.unmarshal(
                    testJson, userResourceDescriptor,
                    BaseResource.BASE_RESOURCE_FACTORY).getScimObject();
      assertNotNull(o);
      SCIMAttribute metaAttr =
              o.getAttribute(SCIMConstants.SCHEMA_URI_CORE, "meta");
      String metaString = metaAttr.toString();
      assertTrue(metaString.contains("department"));
      assertTrue(metaString.contains(
              SCIMConstants.SEPARATOR_CHAR_QUALIFIED_ATTRIBUTE +
                      "department"));
    }
    finally
    {
      System.setProperty(SCIMConstants.IMPLICIT_SCHEMA_CHECKING_PROPERTY, "");
    }
  }

}
