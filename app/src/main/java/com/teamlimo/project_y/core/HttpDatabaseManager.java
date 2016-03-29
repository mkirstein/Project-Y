package com.teamlimo.project_y.core;

/**
 * Created by Project0rion on 27.03.2016.
 */
public class HttpDatabaseManager implements IDatabaseManager {

    @Override
    public int[] queryIds(String tableName) {
        return new int[0];
    }

    @Override
    public int queryRandomId(String tableName) {
        return 0;
    }

    @Override
    public <T> T query(int id, String tableName) {
        return null;
    }

    /*
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000); // milliseconds
            conn.setConnectTimeout(15000); // milliseconds
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
    */
}
