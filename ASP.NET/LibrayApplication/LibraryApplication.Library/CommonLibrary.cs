using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace LibraryApplication.Library
{
    // 게시판 page 버튼 누를때 웹브라우져 주소창에 있던 파라미터내용 사라지는 것 방지하는 기능넣기
    // http://localhost:53716/?searchKind=Publisher&keyword=%EC%B6%9C%ED%8C%90%EC%82%AC
    public class CommonLibrary
    {
        public string GetUrl(string paramType)
        {
            string returnValue = string.Empty;

            switch (paramType)
            {
                case "full":
                    returnValue = HttpContext.Current.Request.Url.AbsoluteUri;
                    //http://localhost:53716/?page=1
                    break;
                case "path":
                    returnValue = HttpContext.Current.Request.Url.AbsolutePath;
                    //http://localhost:53716/?page=1
                    break;

            }

            return returnValue;
        }

        public List<UrlParameter> UrlParameters
        {
            get {

                var returnValue = new List<UrlParameter>();

                string url = this.GetUrl("full");
                // http://localhost:53716/?searchKind=Publisher&keyword=%EC%B6%9C%ED%8C%90%EC%82%AC
                // [0] http://localhost:53716/
                // [1] searchKind=Publisher&keyword=%EC%B6%9C%ED%8C%90%EC%82%AC

                string[] urlArr = url.Split('?');
                string[] paramArr = null;

                if (urlArr.Count() > 1)
                {
                    paramArr = urlArr[1].Split('&');
                    // [0] searchKind=Publisher
                    // [1] keyword=%EC%B6%9C%ED%8C%90%EC%82%AC

                    foreach (var item in paramArr)
                    {
                        var urlParam = new UrlParameter()
                        {
                            Key = item.Split('=')[0],
                            Value = item.Split('=')[1]
                        };
                        returnValue.Add(urlParam);
                    }
                }
                return returnValue;
            }
            
        }


        public string AddUrlParameter(string paramKey, string paramValue)
        {
            string returnValue = string.Empty;

            List<UrlParameter> urlParams = this.UrlParameters;
            UrlParameter urlParameter = urlParams.Where(x => x.Key == paramKey).SingleOrDefault();

            if (urlParameter != null)
                urlParams.Remove(urlParameter);

            urlParams.Add(new UrlParameter()
            {
                Key = paramKey,
                Value = paramValue
            });

            // [0] Key = alpha, Value = alphaValue
            // [1] Key = beta, Value = betaValue
            // [2] Key = gamma, Value = gammaValue

            for (int i = 0; i < urlParams.Count(); i++)
            {
                returnValue += (i == 0) ? "?" : "&";
                returnValue += urlParams[i].Key + "=" + urlParams[i].Value;

                // ?alpha=alphaValue?beta=betaValue&gamma=gammaValue
            }

            return returnValue;
        }
    }
}
