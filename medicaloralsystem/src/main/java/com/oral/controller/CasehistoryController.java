package com.oral.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Casehistory;
import com.oral.common.lang.Result;
import com.oral.common.vo.DemoData;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.filechooser.FileSystemView;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/casehistory")
public class CasehistoryController extends BaseController {

    @RequestMapping("/caseList")
    @ApiOperation(value = "查询", httpMethod = "GET", notes = "查询方法")
    public Result selectcaseAll(String name, Integer size, Integer current) {

        Page<Casehistory> page = casehistoryService.selectBypaName(new Page<>(current, size), name);

        return Result.success(page);

    }

    @RequestMapping("/caseById")
    @ApiOperation(value = "查询", httpMethod = "GET", notes = "根据id打印病历单")
    public Result selectcaseById(Integer Id) {
        Casehistory casehistory = casehistoryService.selectBypaId(Id);
        List<Casehistory> casehistoryList = new ArrayList<>();
        casehistoryList.add(casehistory);
        EasyExcel.write(getPath()+"\\"+casehistory.getName()+"病例单"+".xlsx", Casehistory.class)
                .sheet(casehistory.getName()+"病例单")
                .doWrite(casehistoryList);
        return Result.success(casehistory);
    }

    @PostMapping("/aKeyExport")
    @ApiOperation(value = "查询", httpMethod = "POST", notes = "一键导出全部")
    public Result aKeyExport(){
        List<Casehistory> casehistoryList = casehistoryService.selectAll();
        EasyExcel.write(getPath()+"\\全部病例单"+".xlsx", Casehistory.class)
                .sheet("全部病例单")
                .doWrite(casehistoryList);
        return Result.success("");
    }

}
