package com.example.smartaiexpensetracker.core.di

import com.example.smartaiexpensetracker.core.repos.AuthRepo
import com.example.smartaiexpensetracker.core.repos.AuthRepoImpl
import com.example.smartaiexpensetracker.core.repos.BudgetRepo
import com.example.smartaiexpensetracker.core.repos.BudgetRepoImpl
import com.example.smartaiexpensetracker.core.repos.CategoryRepo
import com.example.smartaiexpensetracker.core.repos.CategoryRepoImpl
import com.example.smartaiexpensetracker.core.repos.ChatRepo
import com.example.smartaiexpensetracker.core.repos.ChatRepoImpl
import com.example.smartaiexpensetracker.core.repos.ExpenseRepo
import com.example.smartaiexpensetracker.core.repos.ExpenseRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepoImpl
    ): AuthRepo

    @Binds
    @Singleton
    abstract fun bindChatRepo(
        impl: ChatRepoImpl
    ): ChatRepo

    @Binds
    @Singleton
    abstract fun bindBudgetRepo(
        impl: BudgetRepoImpl
    ): BudgetRepo

    @Binds
    @Singleton
    abstract fun bindExpenseRepo(
        impl: ExpenseRepoImpl
    ): ExpenseRepo

    @Binds
    @Singleton
    abstract fun bindCategoryRepo(
        impl: CategoryRepoImpl
    ): CategoryRepo
}