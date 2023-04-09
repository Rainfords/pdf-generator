package net.rainfords.mappers

import net.rainfords.model.EmployeeDTO

class EmployeeMapper : DataMapper<EmployeeDTO> {
    override fun setData(payload: EmployeeDTO): Map<String, Any?> {
        return mapOf(
            "employees" to payload.employees
        )
    }
}