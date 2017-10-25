<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/navber.css" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default">
	<div class="container">
		<div class="row">
			<div class="collapse navbar-collapse" id="main-menu">
				<ul class="nav navbar-nav">
					<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
					<%-- <li class="navbarmenu"><a href="${contextPath}/main.do">Home</a></li> --%>
					
					<c:if test="${!empty cateList}">
						<c:forEach var="i" items="${cateList}">
							<li class="dropdown fadeInDown animated d3 navbarmenu">
								<a href="${contextPath}/${i.uriName}.do" class="trigger right-caret">
									<c:out value="${i.name}"></c:out>
								</a>
								
								<c:if test="${i.group_num == cateConfig.groupNo}">
									<c:set var="pageUri" value="${contextPath}/${i.uriName}.do" />
								</c:if>
								
								<c:if test="${!empty boardList.get(i.group_num)}">
									<ul class="firstlevel dropdown-menu sub-menu" style="display: none;">
										<c:forEach var="l" items="${boardList.get(i.group_num)}">
											<li>
												<a href="${contextPath}/${l.uriName}.do">
													<c:out value="${l.name}"/>
												</a>
											</li>
										</c:forEach>
									</ul>
								</c:if>
							</li>
						</c:forEach>
					</c:if>
					<li class="navbarmenu"><a href="#">Sketch quiz</a></li>
				</ul>
			</div>
			
			<div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
		</div>
	</div>
</nav>

<script type="text/javascript">
	$(".navbar.navbar-default ul.nav.navbar-nav > li").removeClass("active");
	$(".navbar.navbar-default ul.nav.navbar-nav > li > a[href='${pageUri}']").parent().addClass("active");
</script>

<script type="text/javascript">
	$(function() {
		$("#main-menu").on("mouseenter", ".dropdown", function() {
			$(this).find(".firstlevel").parent().addClass("active");
			$(this).find(".firstlevel").show();
			$(this).on("mouseleave", function() {
				$(this).find(".firstlevel").hide();
				$(this).find(".firstlevel").parent().removeClass("active");
				$(".navbar.navbar-default ul.nav.navbar-nav > li > a[href='${pageUri}']").parent().addClass("active");
			});
		});
		
		$("#main-menu").on("mouseenter", ".navbarmenu", function() {
			$(this).addClass("active");
			$(this).on("mouseleave", function() {
				$(this).removeClass("active");
				$(".navbar.navbar-default ul.nav.navbar-nav > li > a[href='${pageUri}']").parent().addClass("active");
			});
		});
	});
</script>