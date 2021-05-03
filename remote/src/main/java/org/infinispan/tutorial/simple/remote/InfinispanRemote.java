package org.infinispan.tutorial.simple.remote;

import org.infinispan.client.hotrod.DefaultTemplate;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
/**
 *
 * Infinispan Server includes a default property realm that requires
 * authentication. Create some credentials before you run this tutorial.
 *
 */
public class InfinispanRemote {

   public static void main(String[] args) {
      // Create a configuration for a locally-running server
      ConfigurationBuilder builder = new ConfigurationBuilder();
      /**builder.addServer()
               .host("127.0.0.1")
               .port(ConfigurationProperties.DEFAULT_HOTROD_PORT)
             .security().authentication()
               //Add user credentials.
               .username("username")
               .password("password")
               .realm("default")
               .saslMechanism("DIGEST-MD5");*/
      builder.addServer().host("dg-myproject.apps.cndcluster10.ocp.gsslab.pnq2.redhat.com").port(443)
                .security().authentication().username("developer").password("Tfo1ttLkOM@OpI2i").realm("default").saslMechanism("DIGEST-MD5").ssl().sniHostName("dg-myproject.apps.cndcluster10.ocp.gsslab.pnq2.redhat.com").trustStorePath("/home/gaurkuma/tls.crt").clientIntelligence(ClientIntelligence.BASIC);         
      // Connect to the server
      RemoteCacheManager cacheManager = new RemoteCacheManager(builder.build());
      // Create test cache, if such does not exist
      cacheManager.administration().withFlags(CacheContainerAdmin.AdminFlag.VOLATILE).getOrCreateCache("test", DefaultTemplate.DIST_SYNC);
      // Obtain the remote cache
      RemoteCache<String, String> cache = cacheManager.getCache("test");
      /// Store a value
      cache.put("key1", "Durgesh");
      cache.put("key2", "Varsha");
      cache.put("key3", "Aishwarya");
      // Retrieve the value and print it out
      System.out.printf("key1value = %s\n", cache.get("key1"));
      System.out.printf("key3value = %s\n", cache.get("key3"));
      // Stop the cache manager and release all resources
      cacheManager.stop();
   }

}
