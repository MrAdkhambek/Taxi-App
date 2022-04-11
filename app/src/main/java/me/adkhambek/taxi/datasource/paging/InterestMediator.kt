package me.adkhambek.taxi.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import me.adkhambek.taxi.datasource.models.InterestSubItem
import javax.inject.Inject


class InterestMediator @Inject constructor(
    private val repository: InterestRepository
) : PagingSource<Int, InterestSubItem>() {

    override fun getRefreshKey(state: PagingState<Int, InterestSubItem>): Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, InterestSubItem> {
        val nextPage = params.key ?: 1
        val response = repository.getNextPage(nextPage)

        return LoadResult.Page(
            data = response,
            prevKey = if (nextPage == 1) null else nextPage - 1,
            nextKey = nextPage + 1
        )
    }
}

class InterestRepository @Inject constructor() {

    suspend fun getNextPage(key: Int): List<InterestSubItem> {
        delay(500) // fake loading
        return (1..20).map { id ->
            InterestSubItem(
                id = ((key * 20) + id).toLong(),
                photo = "https://picsum.photos/300/300/?blur=1"
            )
        }
    }
}
