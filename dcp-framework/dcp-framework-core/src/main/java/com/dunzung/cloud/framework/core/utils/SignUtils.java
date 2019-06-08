package com.dunzung.cloud.framework.core.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <pre>
 * 签名相关工具类
 * Created by Binary Wang on 2017-3-23.
 * @author <a href="https://github.com/binarywang">binarywang(Binary Wang)</a>
 * </pre>
 */
public class SignUtils {

    /**
     * 微信公众号支付签名算法(详见:https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=4_3)
     *
     * @param params  参数信息
     * @param signKey 签名Key
     * @return 签名字符串
     */
    public static String createSign(Map<String, Object> params, String signKey) {
        SortedMap<String, Object> sortedMap = new TreeMap<>(params);

        StringBuilder toSign = new StringBuilder();
        for (Object key : sortedMap.keySet()) {
            String value = String.valueOf(params.get(key));
            if (StringUtils.isNotEmpty(value) && !"sign".equals(key) && !"key".equals(key)) {
                toSign.append(key).append("=").append(value).append("&");
            }
        }
        toSign.append("key=").append(signKey);
        System.out.println(toSign.toString());
        return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
    }

    /**
     * 校验签名是否正确
     *
     * @param params  需要校验的参数Map
     * @param signKey 校验的签名Key
     * @return true - 签名校验成功，false - 签名校验失败
     * @see #checkSign(Map, String)
     */
    public static boolean checkSign(Map<String, Object> params, String signKey) {
        String sign = createSign(params, signKey);
        return sign.equals(params.get("sign"));
    }

    public static void main(String[] args) {
        //String data = "{\"sign\":\"8C4DFDFC65A1782CCD5A68659ACADC2F\",\"noncestr\":\"1\",\"parkId\":\"20171127195446304\", \"recordId\":\"158\", \"payTime\":\"2016-11-15 16:46:22\", \"totalCharge\":4000, \"realCharge\":4000, \"orderId\":\"8a9928355849c7ff0158672a26db00\",\"sourceFlag\":1,\"payType\":\"微信\",\"payId\":\"136\"}";
        // String data = "{\"sign\":\"65E35548BF65F3704931DE8E4870B0C7\",\"noncestr\":\"1\",\"parkId\":\"20171127195446304\", \"recordId\":\"158\", \"imageStr\":\"/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wgARCADIAMgDASIAAhEBAxEB/8QAGwAAAQUBAQAAAAAAAAAAAAAAAwABAgQFBgf/xAAZAQADAQEBAAAAAAAAAAAAAAAAAQIDBAX/2gAMAwEAAhADEAAAATxlHz9kkk4smoNXMBFk4TCrEGQaMIsj0NHOYQgyNVRkFQHRztCQMoyHXSVK/GcUReMkxtKLUxFGi0alzui6UPEx2jvC8JbDuKOHayrVJAmVVBGBSFfzjIM4ZoEoqzTjOMUOcJgJpRamItClg07fZdM8ZpegFy28vf00Y/LSeg8tpkXW43rckKnYoQPGMiBsVhQRUPVJnXptEGUoDSiE8Lc5LeNbusDbnosPzUx9Cq9eau0sjRqeK3M4WnNp07tPCRXa14cYqrLMqytXDhsKiHrWZoCThLlOrWj0HBsLfmj646GztWpL5+9cncYNa4jGdK5WjFEAkFatIZlJNnt1Ls6BsVzIihxRZuZW5Wrsq71uPUg3r0MGxUbghUZqGRr4D5NOuIU4NIR6oVW7Qp3lFJ6tyjfz0CWuRIcTzClsU5j0ohNXS1dY1Gqs6houhqO0IvKloPh0BihMmJVk2WdSbekgJXp6GXoxYbFY6bOEiaLWII5Q2L0lGJjXIOaNzUpW8R40IyauQjvOBmaLohBkKuJkrlrZt+KVirYljKIYEhmZ3RHZGDsO8ihp189c9W5XA+d7vC1y5d0WcGlBsRlGdUdGFNnTIdq5mhs1AZAtZtVoD0krNAfQemeLezIyMzsuXw6KN2W9ciwOixujDzOYpSEmBAdhkgsRYmNWEyi86MY9eZGikOzIGdIG9a8l9CDoPN+q4yOk/p3D9XWetz/R8o8vOE7A0mQO8HAharsvqisaUXjoppnBM7MdkgXRc7aF7B5N6H5pn1dL6D5B6cK7xnbefXzcmzpjRlFDpJtkyQyZARkgeSQJJMZJAmSS7TlEs+0/TpLTtvMEtfOwXSBkkNkkDJIIpIP/xAAtEAACAgAFAwMDBAMBAAAAAAABAgADBBESITEFEEETIjIgMEIUFSQzBiNANP/aAAgBAQABBQKGLG7GLw3K9ni8dvD8rGjSuWfKv4vBwPlDFjdmi8NynZ4nHmeH5WNw0rlvyr+LwcfnDFjdmicNynazaLiKobVEF1ZnhuVjcNK5dzV8Xg4/OGLG7NE4bk2rSl+Psshtcn1Gi4hxFtV4fWqSrE+oVh4bivm471vszxW2PyhixuxicOcpdY2Is0tEwV9k/anjdPu1t060TTdh4LayMLifVJ4bhDvdAdiYHgbeGLy3Y8JximyUKzHDYBa0VABpE0iFRLKFaYnBaAleQBzrcwH3WGVjMMsWuacjrnheW7GJLbP9vT1L2iD6CJYMxbnVfUwKPBy8qGzCKYfkDBwvLdjF+ORJ6fTorZtIttslOIuWCyWYsVj9xsZhZ6idQX34I+54OXEqG1pgzh5gOycnvXLlauzB/wDmtIjdQTDxMXXiCF9thprn7hg3NZSdR/owSx4OWiNG3ixhBMovJhniuXI9lNdCqnMImnILnoVQR6FWa4VBMeMgulIeBy5iGO0reNE7LyYZ4rjDM8QPATF97bZNs2axrCJbk7ocyTtGMSPAd9USePJhM17LZK/chnEOp5etyVepiguGsvYeplHfc/1q01QtDKhLYg3Iizx5JhmjYVyi8VUTKa9A/X0ifrsOYMTUYXDrlLMhgwZnNULRXyhfOJy3CTx5mU8L2DbZyxfUB6dWthwlsGCzdRoBaYq0vZnM/pU757Jz4/KEzwpmcXecRfdLKiw9B81r0wtvdZoQ/SD2QbkbId/HmGeF7Vf2NvF/1w4hY1wMNo0+cW2d0Hc91meycj4/l2/ESy1ax0vO6ttpnHXOZQAxFJOLrNGOygEHYxewmWwg489muRQ2LjMWP+PuNF1Whiu7CZTTMJVm3XavcGm04mfZOfGcz2EXjUBHxQEe5mmfYzo1vp9RZA62VGs2DcSqj1CBkOrLqwo7AzOcwbHPYc5bawI15hfOZ9s5n2RzXZVZrQqGGNVMO+FoW+achOqD+BM++cz757Fvs9Mb1en43FrgsM1r3NRi7MLiKrFxFWU6wcun/VnA0D/a6Bd/Gx9LYp2rbD39Lwi2tTQ+HdSGHXWywX2Ad/s9Gt0Y2whUutN2J6VcKsWDAmT9fb/jw1no4nqFvp4KecFf+ow86638n/i8Y6/1Olzz0fEaLPHWLNfUfs//xAAkEQACAgEEAgIDAQAAAAAAAAAAAQIREAMSICExQQQTMDJRIv/aAAgBAwEBPwH8S5uVG43sUrzZZfBukQhuPrij64k9KvBGQ3miLvMjTjUcNUXY1Uhi8jZYo52bkIp35HYov2an7DyxYs0pehYTOjWfgbIjwsUR67Ise70Pf/BmpLc+Fi4Q8Fo6NSVLKysWOf8ADRfoaEjVfdZWd6RufCOpfTJzrpcV3z+LKpGs7m+SfL48fZ8iFPd+TR/Q1l/nn//EACIRAAIBAwUBAQEBAAAAAAAAAAABAgMQERIgITFBEyIwUv/aAAgBAgEBPwG6t5dWl0eXYrRpuQqMT4RJUXHm76PBXRCOqRKSifSR9JEKmSrDTyhHl2rIo+k3lmkRpwS/UBD6sh2RTlh2yhDa8M/jF8CHdXwId10IlbJqtDHphCNJpMWRId10YZyJbUSGKLfRCh/oqrHIrU48ZJU34Y2KjKQqMUZMnY4Y6IQz2eGbOCfRKDjbO2a4I9GdmSUeeN0iL/pLsjv/AP/EADAQAAEDAQYEBQQCAwAAAAAAAAEAAhEDEBIhMUFhIjBAURMgMlJxBGKBkTM0UHKx/9oACAEBAAY/Av8AOZqL4KxkL1jpLzioZwhYuJs7hdjug5uDe7VDs9D36GUewyWAXpKzUAWawsc9lcd6v+9BuvDpDBCcXLLyZJzmNC8RpiCgefUBxulSdOQ9u6uzlzipnDVTZwOxUVMbO69EKdUHIjnuZcIk4bpnwsSV/VfHdY03MO9l6oVd8F43V5jnOag7dPPPcHcXtnRa/uz0ypw+FkEZDZU+EJ2XCC1RjBMFQzLntbMTbkPytIFkrVaFCUeeHWwFFAq64S9XKzY3UFYIu7dBcdmLZWLv0sZC4XW1ZOnRlqlryFh9Q2NwuKqXfCiwieEYdL6yoULc9F+LJt3sjt0OK+qqnYDz1aTu8g8/Fy4QpJVenuCo008viHIZKnW2jmYlcIWJ8jRo8XVBWP7t+3uoGSO3Lz87XjNplB3dQckJqNAOhOKv+I1zR7TbV/16Ok7aE6ocTk0dyvEfi4oOpDHUd0KjMjZV/XJjlVKZPodKvO9GTQjSOaqOJ4gUSzFpzapCA9z+ju6PClOqakqHH+TD82FwMEqi3o6dTs5OcM4gIKck2prkfmymPt6T6U+4WbI0XZPy+bHfaAOV/8QAJBAAAgICAgMAAwEBAQAAAAAAAAERMRAhQVEgYXGBkaGx0cH/2gAIAQEAAT8h58IvHmNFsCvxChQ5FeG2V5K5EUHzDLZSH4AULsqx3zuSuRFPBLGVkX0X0YqEbf8AMX/BAo21OvFCpdlGC+N4VL50ymqpcLs/mIyQfQN1mlb9DNK4BIVo5ZS+ocqOVNeBVhoEZQxAZY1L5VwKVsOrkhOESO38EdEQo97CU4/Yt9k96TkLH7tGLbFFafTNbaG3OI0auRrPBXFTwqQjg4+vS/0RIklLi/o8PcbI8hoo54GuBO00KkiFzN0QNZNNtbg0FwmQwLCTHSaG5w6DooVHycdAhI0KiDk/JDwKhG4hBxEqB5SUuOsq6xFIQh0HSNj0HRQmoLgb5F2lu2KSm53Il75H3DUNwQ7J4Y4uJdIjUoWxMrXJEK2uJ7G/tlBdsEWIwkEMDCo6HQmmNegx6U9HA6HDi+C3bWcJ9T+BYTYpSCVo18Fz2uoP6L0JwxaemKmpMJeCJtt6RTDQ1jy2RNwswaLHQpkYNykCulCN6ZGklC+f3G03xcR9/wBAtN5qVY58WraIn3IWkyHRw/yI29L3ZuFGogRGzZsZMoNaw0KYaORyxkZNwNw0cD/RA1CNx7dC0PsFSUWmL0H7F2mj+khWUnJKvYkNwTM2UIWJHo8cGEaECwKQ/TRaLkyNJkSQ3J99D++ZUyPmDEvUJwRSODIMXNlDQVeDGsyOg3QpBkkIHsjRMooNHO6Ghpb62LPpasl4W+nZMkOLJzA1rn2NBDJ5F4Tbxkw2NEhFrGk2aqEkCHFMTvX32cJIoUFC54j4EQuNHDyJO/Am4EVqc4Q1A8N34CpGoTOzCTNK7JYmkqzTnD7Gt5j9DFhP0ifoPc/QsEKhDiMDJhCViSmXQ4jYdm8PZiVGbckQbPjiELaiJm7NMpYxYRGCw8MZTRcIOhiFyZW4REDa+ZWI2lDYkdGtEgS+W2Pxn9QySwoMcsRoTeBdjlM8Do08+Is/cxvlPsaavV9NQSnJtiHURyJXSFA1B/uYmJe01Q6E2VjfIch4IQSJ4rDFvyhGk/IxhsY5kjQiZXt/+l/g5L+Po6b47YypIdK9Lfb4KQiEpE3irY+iRq5Iu0hpUJ7BksiBfuEOFBehgbYcHuiA3I5PRX8COuSMaE24OEWdILHJURPP3oSKUJJUlwPekRs6CFImBPoQRTTFoQMfJJJJIxJLPeym+rWJtI9cvbfJJyZwml6Y2T0dPoUT6FL+sQRL3mRGEU37DE8sXgpxj+BislwT6Ps3srT9CHTSP7oI9/SQGjslH8zPjJCr6xz4Lws/R7a2PMVPbJhbLXzgW6/Z/oIa9kzlhcMnftbHiB+CvEjvw9+HSyp+ckzP9R6FdZVjb17TqOCVv+ELWybqy/o34TnjLF5uxED2p/lKCHKNOW/yJjuxg3AiEiLE+LeP/9oADAMBAAIAAwAAABCNaBY2y3W8Y9DLH6GuRG2nGuDPwZb1d+NYujxvEIN+W9L3mLYeg4xRulmICUmru5r2ztJwcXEPkKMT9gdEfquy31ErrPHxQv8AAQq4zUi82Hqqt6dMzvJzxPDH2Jpw4BAPbwgBRp1NWCWp5sCuaAcRGDf1HYu8g+gcfBi8/C+CA//EACARAQEBAAIDAAIDAAAAAAAAAAEAERAhIDFBMGFRcYH/2gAIAQMBAT8Qjj5we4nn2jk4+QRfyIHo8fYIwdseHcSOPY7h5Ds06eB7bG3J7cvovZfYVeyQawettEsbvMHq1Zd8MbH+LQZAadfq0Mb62wzTwHvqHuHd6cIIneBA7I1yQEYzSEe7eGvy7vcB4tS2euwHvP8AssO4+n5BCwsRRwS2GXfUo72fU5HHUciQe4MyG6/fEiwuNlnDD7dTBf11ZZCjpfvnCM1kshSRMIPBgy+s2g8Mskzy9l6l6fyHp/cKhh3y/8QAHxEBAQADAQEAAgMAAAAAAAAAAQARITEQQSAwUWGB/9oACAECAQE/EMSQXU2PB4+XDxjJD44jsvnhCMLmV5mIwckRyOZicQbm4tRCy3LFBqwI4NsSTUWBnng7Vw7AM5ttFkbphOGyGvLy4s1/c9yShotG+Nif9EckyXFxdzyDUHPhtxLDNkyxqyPLQRGAxdXyMOWRJrMHNfYfxOREwsa1CENQakuA2zUi8DksE89Bq1YzKueLOBycurfX2Lvcl53BrwG2jou3uwNGvCYYbLzxZWeLOMIijpIb4usQSrPvyfaIZjHjNmGRuX5H8sxj9ji3+f8A/8QAJxABAAICAQQCAwEBAQEBAAAAAQARITFBUWFxgRCRobHB0fDhIPH/2gAIAQEAAT8Q5zSc/i9TEiyn7oLQ7QoprmcnOBgiZQVPGHpm/wATeDT94rHifviuMJTP4Nvjg3FmzMjx8wwUPzhtND4f3/HRN8Uvy4r9IMvMyga9oxtm81Zwm2bZ+2a+pRupnyDLbUFsFplV/UTXEabVPyjfWeSbfdRbWR8Tj7/DTBn4+EKfvFZzxDnN3iKh5nJN0WUGKgVUyc3EOL3lO2gHK6HTzAEQ1Myv6Y+cdzMhX2zVQwcGkyeGL2uKoWdKtY8X4tE8iB4YX5CsNPHDNZqh/CKoYi83DIXiMuY5lkUW+ZWoMvj1mqm3xM3NCBru8ETOHBcBzfWLAnC2/wBR+mfLgJVAlMqYJSn9BiA3LTSZI4AWhpI4WqmVu44rH/EYpWhSi/s1R0r6QIrB0JOLLrLUgvuGRNvj5p0TZNkp6KUXzXphXIBQtatVrtqFHcXl5gYRRE7I+ocL9S1sp3JXZpkSOwrLK45o61zL3fM+UukhAiq0uuINpQh1mM9QSse6TfYAMVmYC5nJs+LRDcm2d+kZj4VMiujxj6mVxYDX/wCwAe2KiJuoRziZXEKcxayLHNdjviWMR9UDk9wfS1K8slS4bxxN6+4VTvTAa9ow2QyjGI4Z5iCrBwiymc4D4WmqCp6NRRBWLQmLrl3BO6pzGLWGB5l3QeuCUDesMkAYGy2XZh7pq3QvMvALwxi4k7G2GOO8WLunpCPdzLO5hivuFayuwjmi4zlDqoT4XNw0fjBuycxeOisF13QBgIXUaagLTW0x9c/UN2USHXlRZ7lcQejY6bIgSNZSWeoqND/4FZVg1z1ZxXKEQz44y+jpl74C8AtzAJ6E4qVtLjcxmbqLiOpaqgWOYFMVNhCFxv8AhS7zYO8BIlIs54p4xcEcTUuHla3qDjDebjbsT02iXxQseXFvEuB3q5MZMF66Dt3IZbBRC+cQO/N0gvjUwYsrteddPMDyIKHfszcqUMYZXpAiiUw3NKioBHwVUq4H5yvwi8BuMf6ORd0yooYcVxBQIxkEqk3Ja/iZyIVVQf7MqyPIyyriw1df2GHWNiI3mNFD7huQhHQkwxhb+ZiIN26uXnaWsLO0rrmEqeYNyZiQYTGVLwQIlkLUq8VVovIokfDmbZn1EagwUxL4ThW31DmBVmIHXRXoxOxuQIdt8yi29zCzavDHtYzu9Yl3eI35lQM9Zk8wC6wmKKu+sq2PPucfEdQRrExRGJXSJ7Qw2sVf9gDd3KNnF9oeIBzK07xVa+9alFJA6tO0LR06A+9+oUVyR/KxDRg8+nvUvq2IDLE2OIuBFpioMzkJlPzZxYoqblWd0SovEK4yYl9jITKDiIAgU1BDo3Y0cGzOHMMjSHUmF5TtuK9JjgawSyigosoaxhRYFUuVeY47lmcW1FZkVHVwIpmQdIvyl9fEAYshyJmShRz3Gq4YSrl7QuTuihSrAxUIO1YE5atrb5ideZ8wjv7vr6iUc33mbExdcxCMlR3qDkhBRxEbcwvN0thI2/iJvEy7TuwHAVmANSJw9QhMMq55iVbW2yPDQqAyrbjrqyxbGJ3cscl/AbgQ1LkvepTWogxWnaPKeZZ6T905YbJQpEXlm74u2I5YmaC2HmyZQVGDX1LBtyyRbL3bi4bZvr1hjSAAcsVmFUKvIJ916jiJ4esobYNyoTBMpelOnmHTK51e0olfxjCyhXWNhidxlFK1/wCQjBHyp3Kq/cEfsTA2zgJGxPJcydItsg5VbKCgmDjv+v7HG7l602ftlxAe6IEB2YiUJ3qU5eoBGYjeYTLPpFNkVdB13DG0/wDOIq8JeIzVYW+MGilVuFr9lPcfpV2G11JUiLcev8PaWKjEEgZ7zNpv+aB0noOCC+MLfiBWmOG5pVXS4cpdsQTPPRgxCRsDcexhoRtqkRC7Q5TOWxi5i3tloFOCQ2XcR2ysy6G7VzpofsXCrrSiBR+tEHWqyd4AsWM10XCBEBRqBAqn3A5pfyhLGmv7KFOHpNy4IbnSZJXuk7zt8Q8Fyhxe3/4ds1EVTmIYJcwiN5xBuhL5236gViNrk0eOXsRTwFlb4ZwcUdIJ8xR6kv08S4IeVvmXcf8AYYuoPWDexA4lJuKw0QIMGcxCJlf/ALDToRZUSiXErxBlxYuKz4xDnXuUnlG+Dv8AYwatYmrtHVx9SvBAq4UWL6gMpKC8JY/YxS8S52OHT9oPtWp6j0e8C8wvqz/JU1KcMWhuGSe5cvVHOIt3sTLDDhOJxFTUvkjqYjK6tFaTT+xxJTAXUwTPJjCgegJkbZtgDdvOSVJK7oLsJQ6CskaxaKfqps/CGZYgVLlxWnoRc95tNDDPxcSo4lRmQ6Ufdj8Fj0Sih439Z2BFV5e0rmADpsch/wBmJuH04H7w+4src/crfNrOi0/kshg8xKuBm5TiLLM3C+dXKD418R2Q+Esh0+QoG6l2i6rq1+bgoU2Wt7Ip0XFUWrH+wSEopMAx9n6JdV0xKBse1pf7MqbivEd7xBKuGbY4jMQTmf/Z\", \"inOrOut\":0}";
        //String data="{\"parkId\":\"20171127195446304\",\"noncestr\":\"1513596613\",\"sign\":\"7015BB655C62907CBF751BF7744162EB\"}";
        String data = "{\"appId\":\"test\",\"smsContent\":\"测试\",\"noncestr\":\"100\",\"mobile\":\"1xxxx\"}";
        String key = "6A204BD89F3C8348AFD5C77C717A097A";
        Map<String, Object> params = JSON.parseObject(data, Map.class);
        System.out.println(SignUtils.createSign(params, key));
        System.out.println(DigestUtils.md5Hex("carType=1&inOut=2&noncestr=1525418795105&outTime=2018-04-2410:26:01&parkId=7a74240d57ce4a4fb8b364a71ea5d1ca&plateId=黑A2222&realCharge=0.00&recordId=2&spareSpaceNum=100&totalCharge=0.00&watchhouseName=离开通道&key=6A204BD89F3C8348AFD5C77C717A097A").toUpperCase());
    }
}
