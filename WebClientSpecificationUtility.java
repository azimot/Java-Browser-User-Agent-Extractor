import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebClientSpecificationUtility {

    private final static List<OsName> osList;
    private final static Pattern mobileCheck = Pattern.compile("Mobile|mini|Fennec|Android|iP(ad|od|hone)");
    private final static Pattern windowsVersion = Pattern.compile("Windows (.*)");
    private final static Pattern androidAndMacVersion = Pattern.compile("(?:Android|Mac OS|Mac OS X|MacPPC|MacIntel|Mac_PowerPC|Macintosh) ([\\.\\_\\d]+)");
    private final static Pattern iOsVersion = Pattern.compile("OS (\\d+)_(\\d+)_?(\\d+)?");

    static {

        osList = new ArrayList<>(27);

        osList.add(new OsName("Windows 10", "(Windows 10.0|Windows NT 10.0)"));
        osList.add(new OsName("Windows 8.1", "(Windows 8.1|Windows NT 6.3)"));
        osList.add(new OsName("Windows 8", "(Windows 8|Windows NT 6.2)"));
        osList.add(new OsName("Windows 7", "(Windows 7|Windows NT 6.1)"));
        osList.add(new OsName("Windows Vista", "Windows NT 6.0"));
        osList.add(new OsName("Windows Server_2003", "Windows NT 5.2"));
        osList.add(new OsName("Windows XP", "(Windows NT 5.1|Windows XP)"));
        osList.add(new OsName("Windows 2000", "(Windows NT 5.0|Windows 2000)"));
        osList.add(new OsName("Windows ME", "(Win 9x 4.90|Windows ME)"));
        osList.add(new OsName("Windows 98", "(Windows 98|Win98)"));
        osList.add(new OsName("Windows 95", "(Windows 95|Win95|Windows_95)"));
        osList.add(new OsName("Windows NT_4.0", "(Windows NT 4.0|WinNT4.0|WinNT|Windows NT)"));
        osList.add(new OsName("Windows CE", "Windows CE"));
        osList.add(new OsName("Windows 3.11", "Win16"));
        osList.add(new OsName("Android", "Android"));
        osList.add(new OsName("Open BSD", "OpenBSD"));
        osList.add(new OsName("Sun OS", "SunOS"));
        osList.add(new OsName("Chrome OS", "CrOS"));
        osList.add(new OsName("Linux", "(Linux|X11(?!.*CrOS))"));
        osList.add(new OsName("iOS", "(iPhone|iPad|iPod)"));
        osList.add(new OsName("Mac OS X", "Mac OS X"));
        osList.add(new OsName("Mac OS", "(Mac OS|MacPPC|MacIntel|Mac_PowerPC|Macintosh)"));
        osList.add(new OsName("QNX", "QNX"));
        osList.add(new OsName("UNIX", "UNIX"));
        osList.add(new OsName("BeOS", "BeOS"));
        osList.add(new OsName("OS/2", "OS\\/2"));
        osList.add(new OsName("Search Bot", "(nuhk|Googlebot|Yammybot|Openbot|Slurp|MSNBot|Ask Jeeves\\/Teoma|ia_archiver)/}"));


    }


    public static void main(String[] args) {

        String userAgent1 = "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1";
        String userAgent2 = "5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Mobile Safari/537.36";
        String userAgent3 = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36";
        String userAgent4 = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36";
        String userAgent5 = "Mozilla/5.0 (Linux; Android 7.1.1; Acer Chromebook R11 (CB5-132T, C738T) Build/R78-12499.51.0; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/78.0.3904.96 Safari/537.36 [Pinterest/Android]";
        String userAgent6 = "Mozilla/5.0 (Linux; Android 9; Acer Chromebook R11 (CB5-132T, C738T) Build/R87-13505.100.0; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/87.0.4280.141 Safari/537.36";
        String userAgent7 = "Mozilla/5.0 (Linux; Android 9; KFMAWI) AppleWebKit/537.36 (KHTML, like Gecko) Silk/81.1.233 like Chrome/81.0.4044.117 Safari/537.36";
        String userAgent8 = "Mozilla/5.0 (X11; OpenBSD amd64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36";
        String userAgent9 = "";

        System.out.println(getClientSpec(userAgent1));
        System.out.println(getClientSpec(userAgent2));
        System.out.println(getClientSpec(userAgent3));
        System.out.println(getClientSpec(userAgent4));
        System.out.println(getClientSpec(userAgent5));
        System.out.println(getClientSpec(userAgent6));
        System.out.println(getClientSpec(userAgent7));
        System.out.println(getClientSpec(userAgent8));
        System.out.println(getClientSpec(userAgent9));

    }

    public static UserAgent getClientSpec(String clientUserAgent) {

        String browserName = "", majorBrowserVersion = "", browserVersion = "", osName = "unknown", osVersion = "unknown";
        int verOffset, nameOffset, ix;
        // mobile version
        boolean isMobile = mobileCheck.matcher(clientUserAgent).find();

        // Opera
        if ((verOffset = clientUserAgent.indexOf("Opera")) != -1) {
            browserName = "Opera";
            browserVersion = clientUserAgent.substring(verOffset + 6);
            if ((verOffset = clientUserAgent.indexOf("Version")) != -1) {
                browserVersion = clientUserAgent.substring(verOffset + 8);
            }
        }
        // Opera Next
        if ((verOffset = clientUserAgent.indexOf("OPR")) != -1) {
            browserName = "Opera";
            browserVersion = clientUserAgent.substring(verOffset + 4);
        }
        // Legacy Edge
        else if ((verOffset = clientUserAgent.indexOf("Edge")) != -1) {
            browserName = "Microsoft Legacy Edge";
            browserVersion = clientUserAgent.substring(verOffset + 5);
        }
        // Edge (Chromium)
        else if ((verOffset = clientUserAgent.indexOf("Edg")) != -1) {
            browserName = "Microsoft Edge";
            browserVersion = clientUserAgent.substring(verOffset + 4);
        }
        // MSIE
        else if ((verOffset = clientUserAgent.indexOf("MSIE")) != -1) {
            browserName = "Microsoft Internet Explorer";
            browserVersion = clientUserAgent.substring(verOffset + 5);
        }
        // Chrome
        else if ((verOffset = clientUserAgent.indexOf("Chrome")) != -1) {
            browserName = "Chrome";
            browserVersion = clientUserAgent.substring(verOffset + 7);
        }
        // Safari
        else if ((verOffset = clientUserAgent.indexOf("Safari")) != -1) {
            browserName = "Safari";
            browserVersion = clientUserAgent.substring(verOffset + 7);
            if ((verOffset = clientUserAgent.indexOf("Version")) != -1) {
                browserVersion = clientUserAgent.substring(verOffset + 8);
            }
        }
        // Firefox
        else if ((verOffset = clientUserAgent.indexOf("Firefox")) != -1) {
            browserName = "Firefox";
            browserVersion = clientUserAgent.substring(verOffset + 8);
        }
        // MSIE 11+
        else if (clientUserAgent.indexOf("Trident/") != -1) {
            browserName = "Microsoft Internet Explorer";
            browserVersion = clientUserAgent.substring(clientUserAgent.indexOf("rv:") + 3);
        }
        // Other browsers
        else {
            browserName = "other";
            browserVersion = "unknown";
        }

        // trim the version string
        if ((ix = browserVersion.indexOf(";")) != -1) {
            browserVersion = browserVersion.substring(0, ix);
        } else if ((ix = browserVersion.indexOf(" ")) != -1) {
            browserVersion = browserVersion.substring(0, ix);
        } else if ((ix = browserVersion.indexOf(")")) != -1) {
            browserVersion = browserVersion.substring(0, ix);
        }

        try {
            majorBrowserVersion = browserVersion.substring(0, browserVersion.indexOf("."));
        } catch (Throwable e) {
            majorBrowserVersion = "unknown";
        }

        // system
        for (OsName osN : osList) {
            if (osN.isMatch(clientUserAgent)) {
                osName = osN.getName();
                break;
            }
        }

        if (osName.contains("Windows")) {
            osVersion = osName;
            osName = "Windows";
        }

        switch (osName) {
            case "Mac OS":
            case "Mac OS X":
            case "Android": {
                Matcher m = androidAndMacVersion.matcher(clientUserAgent);
                m.find();
                osVersion = m.group();
                break;
            }
            case "iOS": {
                Matcher m = iOsVersion.matcher(clientUserAgent);
                m.find();
                osVersion = m.group();
                break;
            }
        }
        if (osVersion.contains(" ")) {
            String arr[] = osVersion.split(" ");
            if (arr.length > 1)
                osVersion = arr[arr.length - 1];
        }
        osVersion = osVersion.replaceAll("_", ".");

        return new UserAgent(osName, osVersion, browserName, majorBrowserVersion, browserVersion, isMobile);
    }

    public static class UserAgent {
        private String osName;
        private String osVersion;
        private String browserName;
        private String browserMajorVersion;
        private String browserEntireVersion;
        private boolean mobileDevice;

        public UserAgent(String osName, String osVersion, String browserName, String browserMajorVersion, String browserEntireVersion, boolean mobileDevice) {
            this.osName = osName;
            this.osVersion = osVersion;
            this.browserName = browserName;
            this.browserMajorVersion = browserMajorVersion;
            this.browserEntireVersion = browserEntireVersion;
            this.mobileDevice = mobileDevice;
        }

        public String getOsName() {
            return osName;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public String getBrowserName() {
            return browserName;
        }

        public String getBrowserMajorVersion() {
            return browserMajorVersion;
        }

        public String getBrowserEntireVersion() {
            return browserEntireVersion;
        }

        public boolean isMobileDevice() {
            return mobileDevice;
        }

        @Override
        public String toString() {
            return "UserAgent{" +
                    "osName='" + osName + '\'' +
                    ", osVersion='" + osVersion + '\'' +
                    ", browserName='" + browserName + '\'' +
                    ", browserMajorVersion='" + browserMajorVersion + '\'' +
                    ", browserEntireVersion='" + browserEntireVersion + '\'' +
                    ", mobileDevice=" + mobileDevice +
                    '}';
        }
    }

    private static class OsName {
        private String name;
        private Pattern pattern;

        public OsName(String name, String pattern) {
            this.name = name;
            this.pattern = Pattern.compile(pattern);
        }

        public boolean isMatch(String clientUserAgent) {
            return pattern.matcher(clientUserAgent).find();
        }

        public String getName() {
            return name;
        }
    }
}
