import org.apache.http.client.methods.HttpGet;

public class FiberApacheEnv implements Env<HttpGet, AutoCloseableFiberApacheHttpClientRequestExecutor<HttpGet>> {
  @Override
  public AutoCloseableFiberApacheHttpClientRequestExecutor<HttpGet> newRequestExecutor(int ioParallelism, int maxConnections, int timeout) throws Exception {
    return new AutoCloseableFiberApacheHttpClientRequestExecutor<>(r -> {
      final int sc = r.getStatusLine().getStatusCode();
      if (sc != 200 && sc != 204)
        throw new AssertionError("Request didn't complete successfully: " + r.getStatusLine().toString());
    }, ioParallelism, maxConnections, timeout);
  }

  @Override
  public HttpGet newRequest(String uri) throws Exception {
    return new HttpGet(uri);
  }
}