package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import services.{BookingServices, ReleasedServices}

class MessageBoardController @Inject()(
                                        components: ControllerComponents,
                                        val reactiveMongoApi: ReactiveMongoApi,
                                        val bookingServices: BookingServices,
                                        val releasedServices: ReleasedServices
                                      ) extends AbstractController (components)
with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {



}
