package com.kbalazsworks.semantic_log_classifier_api.domain.repositories.vector_extension

import com.pgvector.PGvector
import org.jooq.Binding
import org.jooq.BindingGetResultSetContext
import org.jooq.BindingGetSQLInputContext
import org.jooq.BindingGetStatementContext
import org.jooq.BindingRegisterContext
import org.jooq.BindingSQLContext
import org.jooq.BindingSetSQLOutputContext
import org.jooq.BindingSetStatementContext
import org.jooq.Converter
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType
import org.postgresql.util.PGobject
import java.sql.SQLException
import java.sql.SQLFeatureNotSupportedException
import java.sql.Types

class PgVectorBinding : Binding<Any, PGvector> {
    override fun sql(ctx: BindingSQLContext<PGvector>) {
        val value = ctx.value()?.toString() ?: "[]"
        ctx.render().visit(DSL.cast(DSL.`val`(value, SQLDataType.VARCHAR), SQLDataType.OTHER))
    }

    override fun set(ctx: BindingSetStatementContext<PGvector>) {
        val vector = ctx.value()
        val pgObject = PGobject().apply {
            type = "vector"
            value = vector?.toString() ?: "[]"
        }
        ctx.statement().setObject(ctx.index(), pgObject)
    }

    override fun get(ctx: BindingGetResultSetContext<PGvector>) {
        val str = ctx.resultSet().getString(ctx.index())
        ctx.value(parsePgVector(str))
    }

    override fun get(ctx: BindingGetStatementContext<PGvector>) {
        val str = ctx.statement().getString(ctx.index())
        ctx.value(parsePgVector(str))
    }

    override fun set(ctx: BindingSetSQLOutputContext<PGvector>) {
        throw SQLFeatureNotSupportedException("Not used")
    }

    override fun get(ctx: BindingGetSQLInputContext<PGvector>) {
        throw SQLFeatureNotSupportedException("Not used")
    }

    override fun converter(): Converter<Any, PGvector> = object : Converter<Any, PGvector> {
        override fun from(databaseObject: Any?): PGvector? =
            parsePgVector(databaseObject?.toString())

        override fun to(userObject: PGvector?): Any =
            userObject?.toString() ?: "[]"

        override fun fromType(): Class<Any> = Any::class.java
        override fun toType(): Class<PGvector> = PGvector::class.java
    }

    private fun parsePgVector(s: String?): PGvector? {
        if (s.isNullOrBlank()) return null
        return try {
            PGvector(s)
        } catch (e: SQLException) {
            throw RuntimeException("Failed to parse PGvector: $s", e)
        }
    }

    override fun register(ctx: BindingRegisterContext<PGvector>) {
        ctx.statement().registerOutParameter(ctx.index(), Types.OTHER)
    }
}