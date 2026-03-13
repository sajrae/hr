-- Payroll Schedules
INSERT INTO payroll_schedule (id, name, payroll_type, pay_days)
VALUES
    (1, 'Weekly', 'WEEKLY', 'SATURDAY'),
    (2, 'Semi Monthly', 'SEMI_MONTHLY', '15,30'),
    (3, 'Monthly', 'MONTHLY', '30')
ON CONFLICT DO NOTHING;

-- Leave Types
INSERT INTO leave_type (id, name, is_paid)
VALUES
    (1, 'Annual Leave', true),
    (2, 'Sick Leave', true),
    (3, 'Unpaid Leave', false),
    (4, 'Bereavement Leave', true)
ON CONFLICT DO NOTHING;

-- Overtime Rules
INSERT INTO over_time_rule (id, name, multiplier)
VALUES
    (1, 'Regular OT', 1.25),
    (2, 'Special Holiday OT', 1.30),
    (3, 'Regular Holiday OT', 2.00)
ON CONFLICT DO NOTHING;

-- Allowance Types
INSERT INTO allowance_type (id, name, taxable)
VALUES
    (1, 'Transport', false),
    (2, 'Meal', false),
    (3, 'Internet', false),
    (4, 'Housing', true)
ON CONFLICT DO NOTHING;



-- Department
INSERT INTO department (id, code, description,name)
VALUES
    (1, 'IT', 'IT Staff','IT')
    ON CONFLICT DO NOTHING;

-- Position
INSERT INTO position (id, description,title)
VALUES
    (1, 'Senior Dev', 'Manager')
    ON CONFLICT DO NOTHING;

-- Employee Test Data
INSERT INTO employee (id, email, employee_code,first_name,last_name,status,hire_date,phone,department_id,
                      payroll_schedule_code_id,position_id)
VALUES
    (1,'test','1','John','Doe','active','2026-03-12','123',
     1,1,1),
    (2,'test','2','Jane','Doe','active','2026-03-12','123',
     1,2,1)
    ON CONFLICT DO NOTHING;

