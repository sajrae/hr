package com.company.hr.core.mapper;


import com.company.hr.core.dto.EmployeeRegistrationRequest;
import com.company.hr.core.entity.Department;
import com.company.hr.core.entity.Employee;
import com.company.hr.core.entity.PayrollSchedule;
import com.company.hr.core.entity.Position;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(source = "departmentId", target = "department", qualifiedByName = "mapDepartment")
    @Mapping(source = "positionId", target = "position", qualifiedByName = "mapPosition")
    @Mapping(source = "payrollScheduleId", target = "payrollSchedule", qualifiedByName = "mapPayrollSchedule")
    Employee toEntity(EmployeeRegistrationRequest request);

    @Named("mapDepartment")
    default Department mapDepartment(Long id) {
        if (id == null) return null;
        Department d = new Department();
        d.setId(id);
        return d;
    }

    @Named("mapPosition")
    default Position mapPosition(Long id) {
        if (id == null) return null;
        Position p = new Position();
        p.setId(id);
        return p;
    }

    @Named("mapPayrollSchedule")
    default PayrollSchedule mapPayrollSchedule(Long id) {
        if (id == null) return null;
        PayrollSchedule p = new PayrollSchedule();
        p.setId(id);
        return p;
    }
}
