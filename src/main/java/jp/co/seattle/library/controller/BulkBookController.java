package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * Handles requests for the application home page.
 */
@Controller //APIの入り口
public class BulkBookController {
    final static Logger logger = LoggerFactory.getLogger(BulkBookController.class);

    @Autowired
    private BooksService booksService;

    /**
     * @param model
     * @return
     */
    @RequestMapping(value = "/bulkBook", method = RequestMethod.GET) //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String login(Model model) {
        return "bulkBook";
    }

    /**
     * @param locale
     * @param csv_file アップロードされたcsvファイル
     * @param model
     * @return
     */
    @Transactional
    @RequestMapping(value = "/bulkRegist", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String bulkRegist(Locale locale,
            @RequestParam("csv_file") MultipartFile csv_file,
            Model model) {
        logger.info("Welcome insertBooks.java! The client locale is {}.", locale);
        //本の情報を格納するリストを作成
        List<String[]> booklist = new ArrayList<String[]>();
        //エラーを格納するリストを作成
        List<String> errorlist = new ArrayList<String>();
        int a = 0;
        String line = null;
        try {
            InputStream stream = csv_file.getInputStream();
            Reader reader = new InputStreamReader(stream);
            BufferedReader buf = new BufferedReader(reader);
            while ((line = buf.readLine()) != null) {
                //カンマで区切って配列に格納
                String[] data = new String[6];
                data = line.split(",", -1);
                booklist.add(data);
                a++;

                //必須項目のチェック
                if (StringUtils.isNullOrEmpty(data[0]) || StringUtils.isNullOrEmpty(data[1])
                        || StringUtils.isNullOrEmpty(data[2]) || StringUtils.isNullOrEmpty(data[3])) {
                    errorlist.add(a + "行目に必須項目がありません");
                }
                //文字列形式のチェック
                //出版日のチェック
                try {
                    DateFormat df = new SimpleDateFormat("yyyyMMdd");
                    df.setLenient(false);
                    df.format(df.parse(data[3]));

                } catch (ParseException p) {
                    errorlist.add(a + "行目の出版日は半角数字のYYYYMMDD形式で入力してください");
                }

                //ISBNのチェック
                boolean isIsbn = data[4].matches("([0-9]{10}||[0-9]{13})?$");
                if (!isIsbn) {
                    errorlist.add(a + "行目のISBNの桁数または半角数字が正しくありません");
                }
            }
                //エラーメッセージの表示
                if (!(errorlist.size() == 0)) {
                    model.addAttribute("errorlist", errorlist);
                    return "bulkBook";
                }

            for (int i = 0; i < booklist.size(); i++) {
                BookDetailsInfo bookInfo = new BookDetailsInfo();
                bookInfo.setTitle(booklist.get(i)[0]);
                bookInfo.setAuthor(booklist.get(i)[1]);
                bookInfo.setPublisher(booklist.get(i)[2]);
                bookInfo.setPublishDate(booklist.get(i)[3]);
                bookInfo.setIsbn(booklist.get(i)[4]);
                bookInfo.setDescription(booklist.get(i)[5]);

                booksService.registBook(bookInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("resultMessage", "登録完了");

        return "bulkBook";
        //CSVで入力された値

    }

}
