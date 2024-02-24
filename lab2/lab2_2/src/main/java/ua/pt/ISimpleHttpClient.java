package ua.pt;

import java.io.IOException;

interface ISimpleHttpClient {
    public String doHttpGet(String url) throws IOException;
}
