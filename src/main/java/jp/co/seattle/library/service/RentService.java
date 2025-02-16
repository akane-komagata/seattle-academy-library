package jp.co.seattle.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 貸出コントローラー
 * @author akane
 *
 */
@Service
public class RentService {
    final static Logger logger = LoggerFactory.getLogger(RentService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 貸出登録をする
     * @param bookId 書籍ID
     */
    public void rentBook(int bookId) {
        String sql = "INSERT INTO rent(BOOKS_ID) VALUES(" + bookId + ")";
        jdbcTemplate.update(sql);

    }

    /**
     * 貸出情報を確認する
     * @param bookId
     * @return countBook
     */
    public int rentCount(int bookId) {
        // 貸出書籍のカウントをする
        String sql = "SELECT COUNT(ID)FROM rent WHERE BOOKS_ID =" + bookId;
        int countBook = jdbcTemplate.queryForObject(sql, int.class);
        return countBook;
    }

    /**
     * 貸出IDの削除機能
     * @param bookId
     */
    public void deletingRentBook(int bookId) {
        String sql = "DELETE FROM rent WHERE BOOKS_ID =" + bookId + ";";
        jdbcTemplate.update(sql);
    }

}
