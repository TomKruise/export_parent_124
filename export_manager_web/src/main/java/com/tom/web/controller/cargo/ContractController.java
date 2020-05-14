package com.tom.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.tom.domain.cargo.Contract;
import com.tom.domain.cargo.ContractExample;
import com.tom.domain.system.User;
import com.tom.service.cargo.ContractService;
import com.tom.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/cargo/contract", name = "购销合同")
public class ContractController extends BaseController {
    @Reference
    private ContractService contractService;

    @RequestMapping(path = "/list", name = "合同查询")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        ContractExample contractExample = new ContractExample();

        ContractExample.Criteria criteria = contractExample.createCriteria();

        criteria.andCompanyIdEqualTo(getCompanyId());

        User user = getUser();
        Integer degree = user.getDegree();

        if (degree == 2) {//管理下属部门
            criteria.andCreateDeptLike(user.getDeptId() + "%");//根据部门id模糊查询(包含子部门)
        } else if (degree == 3) {//管理本部门
            criteria.andCreateDeptEqualTo(user.getDeptId());
        } else if (degree == 4) {//普通员工
            criteria.andCreateByEqualTo(user.getId());
        }//不加条件是企业boss

        contractExample.setOrderByClause("create_time desc");

        PageInfo page = contractService.findByPage(pageNum, pageSize, contractExample);
        request.setAttribute("page", page);
        return "cargo/contract/contract-list";
    }

    @RequestMapping(path = "/toAdd", name = "新增页面")
    public String toAdd() {
        return "cargo/contract/contract-add";
    }

    @RequestMapping(path = "/edit", name = "save or update")
    public String edit(Contract contract) {
        contract.setCompanyId(getCompanyId());
        contract.setCompanyName(getCompanyName());
        if (StringUtils.isEmpty(contract.getId())) {
            User user = getUser();

            contract.setCreateBy(user.getId());
            contract.setCreateDept(user.getDeptId());
            contractService.save(contract);
        } else {
            contractService.update(contract);
        }

        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping(path = "/toUpdate", name = "update")
    public String toUpdate(String id) {
        Contract contract = contractService.findById(id);

        request.setAttribute("contract", contract);

        return "cargo/contract/contract-update";
    }

    @RequestMapping(path = "/delete", name = "delete")
    public String delete(String id) {
        contractService.delete(id);
        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping(path = "/submit", name = "submit")
    public String submit(String id) {
        Contract contract = new Contract();

        contract.setId(id);

        contract.setState(1);

        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping(path = "/cancel", name = "cancel")
    public String cancel(String id) {
        Contract contract = new Contract();

        contract.setId(id);

        contract.setState(0);

        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }
}
