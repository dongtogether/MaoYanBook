package com.example.maoyan.controller;

import com.example.maoyan.entity.Book;
import com.example.maoyan.entity.CollectBook;
import com.example.maoyan.entity.Search;
import com.example.maoyan.result.Result;
import com.example.maoyan.result.ResultFactory;
import com.example.maoyan.service.impl.BookServiceImpl;
import com.example.maoyan.service.impl.CollectBookServiceImpl;
import com.example.maoyan.utils.OrderCodeFactory;
import com.example.maoyan.utils.PageRequest;
import com.example.maoyan.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

@RestController
public class LibraryController {
    @Autowired
    BookServiceImpl bookService;

    @Autowired
    CollectBookServiceImpl collectBookService;

    /**
     * 生成指定长度随机字符串
     * @param length
     * @return
     */
    public String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    /**
     * 初始加载所有图书列表
     * @return
     * @throws Exception
     */
    @GetMapping("/api/books")
    public Result list() throws Exception {
        List<Book> bookList = bookService.list();
        return ResultFactory.buildSuccessResult(bookList);

    }

    /**
     * 分页加载所有图书列表
     * @return
     * @throws Exception
     */
    @GetMapping("/api/booksByPage/{size}/{page}")
    public Result listByPage(@PathVariable("size") int size,
                             @PathVariable("page") int page) throws Exception {
        PageRequest pageQuery =new PageRequest();
        pageQuery.setPageSize(size);
        pageQuery.setPageNum(page);

        PageResult pageResult =bookService.listByPage(pageQuery );
        return ResultFactory.buildSuccessResult(pageResult);
    }

    /**
     * 初始获取所有收藏图书编号
     * @return
     * @throws Exception
     */
    @GetMapping("/api/books/collectBook/{userId}")
    public Result listCollectionBook(@PathVariable("userId") String userId) throws Exception {
        List<CollectBook> collectBookList = collectBookService.selectByUserId(userId);
        //取出每个收藏的书号
        ArrayList<String> bookList = new ArrayList<>();
        if(collectBookList != null){
            for(CollectBook c : collectBookList){
                bookList.add(c.getBookId());
            }
        }
        return ResultFactory.buildSuccessResult(bookList);
    }

    /**
     * 列出特定用户收藏的图书详情
     * @param userId
     * @param size
     * @param page
     * @return
     * @throws Exception
     */
    @GetMapping("/api/books/collectBook/{userId}/{size}/{page}")
    public Result listCollectBooks(@PathVariable("userId") String userId,
                              @PathVariable("size") int size,
                              @PathVariable("page") int page) throws Exception {

        PageRequest pageQuery =new PageRequest();
        pageQuery.setPageSize(size);
        pageQuery.setPageNum(page);

        PageResult pageResult =bookService.queryCollectionBooksByPage(pageQuery,userId );
        return ResultFactory.buildSuccessResult(pageResult);
    }

    /**
     * 添加图书收藏
     * */
    @PostMapping("/api/book/addCollectBook")
    public Result addCollection(@RequestBody CollectBook cb) throws Exception {
        String id = OrderCodeFactory.generateCollectBookID();
        CollectBook collectBook = new CollectBook();
        collectBook.setCollectId(id);
        collectBook.setUserId(cb.getUserId());
        collectBook.setBookId(cb.getBookId());
        collectBookService.addCollectBook(collectBook);
        return ResultFactory.buildSuccessResult("收藏成功");
    }

    /**
     * 取消图书收藏
     * */
    @PostMapping("/api/book/deleteCollectBook")
    public Result deleteCollection(@RequestBody CollectBook cb) throws Exception {

        collectBookService.deleteCollectBook(cb.getUserId(),cb.getBookId());
        return ResultFactory.buildSuccessResult("收藏取消了");
    }


    /**
     * 根据类别获取图书列表
     * @param cid
     * @return
     * @throws Exception
     */
    @GetMapping("/api/categories/{cid}/books")
    public Result listByCategory(@PathVariable("cid") int cid) throws Exception {
        if (0 != cid) {
            List<Book> bookListByCid = bookService.listByCategory(cid);
            return ResultFactory.buildSuccessResult(bookListByCid);
        } else {
            return list();
        }
    }

    /**
     * 关键字查询图书
     * @param s
     * @return
     * @throws Exception
     */
    @PostMapping("/api/search")
    public Result searchResult(@RequestBody Search s) throws Exception {
        // 关键字为空时查询所有书籍
        if ("".equals(s.getKeywords())) {
            return list();
        } else {
            List<Book> bookListByKeyword = bookService.Search(s.getKeywords());
            return ResultFactory.buildSuccessResult(bookListByKeyword);
        }
    }

    /**
     * 图书信息修改
     * @param book
     * @return
     * @throws Exception
     */
    @PostMapping("/api/books")
    public Result addOrUpdateBook(@RequestBody Book book) throws Exception {
        //id存在则是修改，没有则新增
        if(book.getBookId() != null && book.getBookId() != ""){
            bookService.updateBookInfo(book);
        } else {
            String id = OrderCodeFactory.generateBookID();
            book.setBookId(id);
            bookService.addBook(book);
        }

        return ResultFactory.buildSuccessResult("更改成功");
    }

    /**
     * 图书删除
     * @param book
     * @throws Exception
     */
    @PostMapping("/api/delete")
    public Result delete(@RequestBody Book book) throws Exception {
        bookService.deleteById(book.getBookId());
        return ResultFactory.buildSuccessResult("删除成功");
    }

    /**
     * 上传图片
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("api/covers")
    public String coversUpload(MultipartFile file) throws Exception {
        String folder = "D:/Ideal/maoyan/img";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, getRandomString(6) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8081/api/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}


