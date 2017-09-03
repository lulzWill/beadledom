package com.cerner.beadledom.client.resteasy

import okhttp3.{Protocol, Request, Response}
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSpec, MustMatchers}

/**
 * Specs for DefaultServiceUnavailableRetryStrategy.
 *
 * @author John Leacox
 */
class DefaultServiceUnavailableRetryStrategySpec
    extends FunSpec with MustMatchers with MockitoSugar with RetryRequestBehaviors {
  describe("DefaultServiceUnavailableRetryStrategy") {
    describe("#getRetryIntervalMillis") {
      it("returns 1000 milliseconds as the retry interval") {
        val retryStrategy = new DefaultServiceUnavailableRetryStrategy()

        retryStrategy.getRetryIntervalMillis(0) mustBe 1000
      }
    }

    val retryStrategy = new DefaultServiceUnavailableRetryStrategy()

    describe("#shouldRetry") {
      describe("with status code 500 and executionCount 0") {
        it must behave like nonRetryableRequest(retryStrategy, 500, 0)
      }

      describe("with status code 500 and executionCount 1") {
        it must behave like nonRetryableRequest(retryStrategy, 500, 1)
      }

      describe("with status code 500 and executionCount 2") {
        it must behave like nonRetryableRequest(retryStrategy, 500, 2)
      }

      describe("with status code 599 and executionCount 0") {
        it must behave like nonRetryableRequest(retryStrategy, 599, 0)
      }

      describe("with status code 503 and executionCount 1") {
        it must behave like retryableRequest(retryStrategy, 1)
      }

      describe("with status code 503 and executionCount 2") {
        it must behave like retryableRequest(retryStrategy, 2)
      }

      describe("with status code 503 and executionCount 3") {
        it must behave like retryableRequest(retryStrategy, 3)
      }

      describe("with status code 503 and executionCount 4") {
        it must behave like nonRetryableRequest(retryStrategy, 503, 4)
      }

      describe("with status code 500 and executionCount 3") {
        it must behave like nonRetryableRequest(retryStrategy, 500, 3)
      }

      describe("with status code 499 and executionCount 0") {
        it must behave like nonRetryableRequest(retryStrategy, 499, 0)
      }

      describe("with status code 600 and executionCount 0") {
        it must behave like nonRetryableRequest(retryStrategy, 600, 0)
      }

      describe("with status code 499 and executionCount 3") {
        it must behave like nonRetryableRequest(retryStrategy, 499, 3)
      }
    }
  }
}

trait RetryRequestBehaviors extends MustMatchers with MockitoSugar {
  this: FunSpec =>

  def retryableRequest(retryStrategy: DefaultServiceUnavailableRetryStrategy,
      executionCount: Int): Unit = {
    require(executionCount < 4)

    it(s"returns true") {
      val response = mockResponse(503)

      retryStrategy.shouldRetry(response, executionCount) mustBe true
    }
  }

  def nonRetryableRequest(retryStrategy: DefaultServiceUnavailableRetryStrategy, statusCode: Int,
      executionCount: Int): Unit = {
    require(statusCode != 503 || !(executionCount < 4))

    it(s"returns false") {
      val response = mockResponse(statusCode)

      retryStrategy.shouldRetry(response, executionCount) mustBe false
    }
  }

  private def mockResponse(statusCode: Int): Response = {
    val request = new Request.Builder()
        .url("http://example.com")
        .build()

    val response = new Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_1_1)
        .message("mock")
        .code(statusCode)
        .build()

    response
  }
}
