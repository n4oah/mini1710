<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/navber.css" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageUri" value="${pageContext.request.contextPath}/board/list.do?cateNo=${cateNo}" />
<nav class="navbar navbar-default">
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

	<c:choose>
		<c:when test="${!empty sessionScope.user}">
			<span class="navbar-login"><a href="${contextPath}/login/logout.do">Logout</a></span>
		</c:when>
		<c:otherwise>
			<span class="navbar-login"><a href="${contextPath}/login/signoutform.do">Sign Out</a></span>
			<span class="navbar-login"><a href="${contextPath}/login/signinform.do">Sign In</a></span>
		</c:otherwise>
	</c:choose>
	
	<div class="container">
		<div class="row">
			<div class="collapse navbar-collapse" id="main-menu">
				<ul class="nav navbar-nav">
					<li class="navbarmenu"><a href="${pageContext.request.contextPath}/home.do">Home</a></li>
					<c:if test="${!empty applicationScope.navCateList}">
						<c:forEach var="i" items="${applicationScope.navCateList}">
							<li class="dropdown fadeInDown animated d3 navbarmenu">
								<a href="${contextPath}/${i.uriName}.do?cateNo=${i.cateNo}" class="trigger right-caret">
									<c:out value="${i.name}"></c:out>
								</a>
								<c:if test="${!empty applicationScope.navBoardList.get(i.groupNo)}">
									<ul class="firstlevel dropdown-menu sub-menu" style="display: none;">
										<c:forEach var="l" items="${applicationScope.navBoardList.get(i.groupNo)}">
											<li>
												<a href="${contextPath}/${l.uriName}.do?cateNo=${l.cateNo}">
													<c:out value="${l.name}"/>
												</a>
											</li>
										</c:forEach>
									</ul>
								</c:if>
							</li>
						</c:forEach>
					</c:if>
					<li class="navbarmenu"><a href="${pageContext.request.contextPath}/quiz.do">Sketch quiz</a></li>
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
	$(".navbar.navbar-default ul.nav.navbar-nav > li a[href='${pageUri}']").closest("li.navbarmenu").addClass("active");
	
	$(function() {
		$("#main-menu").on("mouseenter", ".dropdown", function() {
			$(this).find(".firstlevel").parent().addClass("active");
			$(this).find(".firstlevel").show();
			$(this).on("mouseleave", function() {
				$(this).find(".firstlevel").hide();
				$(this).find(".firstlevel").parent().removeClass("active");
				$(".navbar.navbar-default ul.nav.navbar-nav > li a[href='${pageUri}']").closest("li.navbarmenu").addClass("active");
			});
		});
		
		$("#main-menu").on("mouseenter", ".navbarmenu", function() {
			$(this).addClass("active");
			$(this).on("mouseleave", function() {
				$(this).removeClass("active");
				$(".navbar.navbar-default ul.nav.navbar-nav > li a[href='${pageUri}']").closest("li.navbarmenu").addClass("active");
			});
		});
	});
</script>