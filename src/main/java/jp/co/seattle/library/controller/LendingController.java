package jp.co.seattle.library.controller;

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

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentService;

@Controller
public class LendingController {
    final static Logger logger = LoggerFactory.getLogger(LendingController.class);

    @Autowired
    private RentService rentService;

    @Autowired
    private BooksService booksService;

    /**
     * 貸出書籍の登録
     * @param locale
     * @param bookId
     * @param model
     * @return　details
     */
    @Transactional
    @RequestMapping(value = "/rentBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String borrowingBook(Locale locale,
            @RequestParam("bookId") int bookId,
            Model model) {
        logger.info("Welcome borrowingBook.java! The client locale is {}.", locale);

        //貸出書籍の登録
        rentService.rentBook(bookId);
        //書籍の詳細情報の取得
        model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
        model.addAttribute("disabled", "disabled");

        model.addAttribute("lendingStatus", "貸出中");
        //貸出登録後、詳細画面のままにする
        return "details";
    }

    @Transactional
    @RequestMapping(value = "/returnBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String returnBook(Locale locale,
            @RequestParam("bookId") int bookId,
            Model model) {
        logger.info("Welcome returnBook.java! The client locale is {}.", locale);
        //返却する（貸出IDの削除）
        rentService.deletingRentBook(bookId);
        //書籍の詳細情報の取得
        model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
        model.addAttribute("returnDisabled", "disabled");

        model.addAttribute("lendingStatus", "貸出可");
        //貸出ID削除後後、詳細画面のままにする
        return "details";
    }

}
