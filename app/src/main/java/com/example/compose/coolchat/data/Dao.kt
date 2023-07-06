package com.example.compose.coolchat.data

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.launch

enum class TypeSelector(val value: Int) {
    PROMPT_ROLE(1),
    PROMPT_COMMAND(2)
}

enum class LanguageSelector {
    English,
    CHINESE
}

@Entity(tableName = "promptCommand")
data class PromptCommand(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "label") val label: String?,
    @ColumnInfo(name = "type") val type: Int = TypeSelector.PROMPT_ROLE.value,
    @ColumnInfo(name = "lang") val lang: Int = LanguageSelector.English.ordinal,
    @ColumnInfo(name = "priority") val priority: Int = 1,
    @ColumnInfo(name = "prompt") val prompt: String?,
    @ColumnInfo(name = "model") val model: String = "gpt-3.5-turbo",
    @ColumnInfo(name = "temperature") val temperature: Double = 0.2,
    @ColumnInfo(name = "desc") val desc: String?
)


@Dao
interface PromptCommandDao {
    @Query("SELECT * FROM promptCommand")
    fun getAll(): List<PromptCommand>

    @Query("SELECT prompt from  promptCommand Where type = :type and label = :command")
    fun getPrompt(type: Int, command : String) : String

    @Insert
    fun insertAll(vararg users: PromptCommand)

    @Insert
    fun insert(users: PromptCommand)

    @Delete
    fun delete(user: PromptCommand)
}


@Database(entities = [PromptCommand::class], version = 1, exportSchema = false)
abstract class PromptCommandDatabase : RoomDatabase() {

    abstract fun promptCommandDao(): PromptCommandDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PromptCommandDatabase? = null

        fun getDatabase(context: Context): PromptCommandDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PromptCommandDatabase::class.java,
                    "prompt.db"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}


// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PromptRepository(private val promptCommandDao: PromptCommandDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: List<PromptCommand> = promptCommandDao.getAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(promptCommand: PromptCommand) {
        promptCommandDao.insert(promptCommand)
    }
}

class PromptViewModel(private val repository: PromptRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.


    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: PromptCommand) = viewModelScope.launch {
        repository.insert(word)
    }
}

class PromptViewModelFactory(private val repository: PromptRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PromptViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PromptViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


